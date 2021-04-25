package space.colaba.security.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import space.colaba.security.util.TokenUtil.TokenExceptionCode.EXPIRED_REFRESH_TOKEN
import space.colaba.security.util.TokenUtil.TokenExceptionCode.EXPIRED_TOKEN
import space.colaba.security.util.TokenUtil.TokenExceptionCode.INVALID_TOKEN
import space.colaba.security.util.TokenUtil.TokenExceptionCode.NOT_ACCESS_TOKEN
import space.colaba.security.util.TokenUtil.TokenExceptionCode.NOT_REFRESH_TOKEN
import space.colaba.security.util.TokenUtil.TokenExceptionCode.TOKEN_CLAIMS_STRING_IS_EMPTY
import space.colaba.security.util.TokenUtil.TokenExceptionCode.UNSUPPORTED_TOKEN
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.ZonedDateTime.now
import java.util.*
import java.util.UUID.fromString
import java.util.concurrent.TimeUnit


object TokenUtil {
    const val fbi = "fbi"
    const val TOKEN_PREFIX = "Bearer "
    const val SCOPE_KEY = "scope"
    const val tokenExpiration = 7L

    const val DEV_TOKEN =
        "eyJhbGciOiJIUzUxMiJ9.eyJzY29wZSI6IltURVNULCBHVUVTVCwgVVNFUiwgQURNSU5dIiwiYXVkIjoiLTEiLCJzdWIiOiItMSIsImlhdCI6MTYwODEwNTAzNiwiZXhwIjoxNjM5NjQxMDM2fQ.6loaHjcX9Rv2I_RXSzwCJ4fLHB0gy_FFM9_jEMEo1E7dsR8AjBGQst8RsabO74zhN6ChbZf2fZa5DzNvlameGg"


    infix fun UUID.toJwtRefreshToken(jwtAccessToken: String): String = tryConvertClaims { getBody(jwtAccessToken) }
            .toJwtToken(toString())

    fun toJwtAccessToken(jwtRefreshTokenToken: String): String = tryConvertClaims { getBody(jwtRefreshTokenToken) }
        .let {
            it.toJwtToken(it.subject)
        }

    private fun Claims.toJwtToken(aud: String = audience) = Jwts.builder()
        .setAudience(aud)
        .setSubject(subject)
        .claim(SCOPE_KEY, this[SCOPE_KEY] as String)
        .setIssuer(issuer)
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date(Date().time + TimeUnit.DAYS.toMillis(tokenExpiration)))
        .signWith(SignatureAlgorithm.HS512, fbi)
        .compact()

    fun refreshTokenUuidValue(jwtRefreshToken: String): UUID = try {
        fromString(audience(jwtRefreshToken))
    } catch (e: IllegalArgumentException) {
        throw RuntimeException(NOT_REFRESH_TOKEN.name)
    }

    fun userId(token: String): String = try {
        audience(token)
    } catch (e: NumberFormatException) {
        System.err.println("AUDIENCE in token: [${audience(token)} is not valid user id]")
        throw RuntimeException(NOT_ACCESS_TOKEN.name)
    }

    fun userIdOrNull(token: String?): String? = token?.let { userId(token) }

    fun userIdFromRefreshToken(refreshToken: String): String = getSubject(refreshToken)

    fun getBody(token: String): Claims = parseClaims(token).body

    fun getExpiration(token: String): ZonedDateTime = ZonedDateTime.ofInstant(
        getBody(token).expiration.toInstant(), ZoneId.systemDefault()
    )

    fun isExpired(token: String) = getExpiration(token).isBefore(now())

    fun ifExpiredThrow(token: String) =
        if (isExpired(token)) throw RuntimeException(EXPIRED_REFRESH_TOKEN.name) else token

    fun hasRole(token: String, roleName: String = "USER"): Boolean = getRolesStr(token).contains(roleName)

    fun admin(jwtToken: String): Boolean = hasRole(jwtToken, "ADMIN")
    fun adminOrThrow(adminToken: String): Boolean = admin(adminToken).apply{  if (!this) throw RuntimeException("NOT_ADMIN") }

    fun getSubject(token: String): String = getBody(token).subject

    fun getAuthorities(token: String) = getBody(token)[SCOPE_KEY] as String?

    fun getRoles(token: String) = getRolesStr(token).map { SimpleGrantedAuthority(it) }.toSet()

    fun getRolesStr(token: String): Set<String> = getAuthorities(token)
        ?.removeSurrounding("[", "]")?.replace(" ", "")?.split(",")?.toSet()
        ?: setOf("GUEST")

    private fun audience(token: String): String = getBody(token).audience

    // UNUSED
    fun getIssuer(token: String): String? = getBody(token).issuer
    // UNUSED
    fun isSecuredHttpsProtocol(token: String) = getIssuer(token)?.startsWith("https") ?: false

    private fun parseClaims(bearerToken: String): Jws<Claims> {
        try {
            val tokenOnly = bearerToken.removePrefix(TOKEN_PREFIX)
            return Jwts.parser().setSigningKey(fbi).parseClaimsJws(tokenOnly)
        } catch (e: Exception) {
            val code: TokenExceptionCode = when (e) {
                is SignatureException -> EXPIRED_TOKEN
                is ExpiredJwtException -> EXPIRED_TOKEN
                is MalformedJwtException -> INVALID_TOKEN
                is UnsupportedJwtException -> UNSUPPORTED_TOKEN
                is IllegalArgumentException -> TOKEN_CLAIMS_STRING_IS_EMPTY
                else -> throw e
            }
            throw RuntimeException(code.name)
        }
    }

    enum class TokenExceptionCode  {
        INVALID_TOKEN,
        EXPIRED_TOKEN,
        NOT_ACCESS_TOKEN,
        NOT_REFRESH_TOKEN,
        UNSUPPORTED_TOKEN,
        EXPIRED_REFRESH_TOKEN,
        TOKEN_CLAIMS_STRING_IS_EMPTY,
        ;
    }

    private inline infix fun <reified T> tryConvertClaims(function: () -> T): T = try {
        function()
    } catch (e: RuntimeException) {
        System.err.println("Error when converting [refresh_token] or [access_token]")
        throw e
    }

}
