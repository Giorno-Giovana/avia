package space.colaba.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import space.colaba.security.util.TokenUtil
import space.colaba.security.util.accessJwtToken
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


internal class JwtTokenFilter : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        SecurityContextHolder.getContext().authentication = TokenAuthentication(request.accessJwtToken())
        chain.doFilter(request, response)
    }
}

internal class TokenAuthentication(private val token: String) : AbstractAuthenticationToken(TokenUtil.getRoles(token)) {
    override fun isAuthenticated() = true
    override fun getCredentials() = token
    override fun getPrincipal() = TokenUtil.userId(token)
}
