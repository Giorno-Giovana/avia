package space.colaba.security.util

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import space.colaba.config.domain
import space.colaba.security.util.TokenKeys.ACCESS_TOKEN_KEY
import space.colaba.security.util.TokenKeys.NUXT_ACCESS_TOKEN_KEY
import space.colaba.security.util.TokenUtil.TOKEN_PREFIX
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

fun HttpServletRequest.userId(): String = TokenUtil.userId(accessJwtToken())
fun HttpServletRequest.userIdOrNull(): String? = TokenUtil.userIdOrNull(accessJwtTokenOrNull())

fun HttpServletRequest.accessJwtTokenOrNull(): String? = (
        getHeader("Authorization")
            ?: getCookie(NUXT_ACCESS_TOKEN_KEY)
            ?: getCookie(ACCESS_TOKEN_KEY)
            ?: getCookie("Authorization")
            ?: getHeader(ACCESS_TOKEN_KEY)
        )?.removePrefix(TOKEN_PREFIX)?.let { if (it.length < 10) null else it }

fun HttpServletRequest.accessJwtToken(): String = accessJwtTokenOrNull()
    ?: throw RuntimeException("[$method] $requestURI \n 'Authorization' not exist in request)")

infix fun HttpServletRequest.getCookie(name: String): String? = cookies?.firstOrNull { it?.name == name }?.value

fun HttpServletResponse.setAccessToken(jwtAccessToken: String): String {
    val age = 60 * 60 * 24 * 7
    addInCookie(ACCESS_TOKEN_KEY, jwtAccessToken, age)
    addInCookie(NUXT_ACCESS_TOKEN_KEY, jwtAccessToken, age)
    setHeader(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + jwtAccessToken)
    setHeader(ACCESS_TOKEN_KEY, jwtAccessToken)
    return jwtAccessToken
}

fun HttpServletResponse.addInCookie(key: String, jwtToken: String, age: Int) {
    val tokenWithoutPrefix = jwtToken.removePrefix(TOKEN_PREFIX)
    val cookie = ResponseCookie
        .from(key, tokenWithoutPrefix).maxAge(age.toLong())
        .domain(domain).path("/")
//        .sameSite("Strict").secure(true)
        .httpOnly(false)
    addHeader(HttpHeaders.SET_COOKIE, cookie.build().toString())
}
