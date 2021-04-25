package space.colaba.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import space.colaba.security.util.UserAuditorAware
import space.colaba.service.UserService


@Configuration @EnableWebSecurity
class SecurityConfig(private val userService: UserService) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http {
            csrf { disable() }
            authorizeRequests { authorize(anyRequest, authenticated) }
            addFilterAt(JwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
        }
    }
    override fun configure(webSecurity: WebSecurity)           { webSecurity.ignoring().antMatchers(*whitelist) }
    override fun configure(auth: AuthenticationManagerBuilder) { auth.authenticationProvider(authProvider()) }
    @Bean override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()
    @Bean fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    @Bean fun authProvider   (): DaoAuthenticationProvider = DaoAuthenticationProvider().apply { setUserDetailsService(userService); setPasswordEncoder(BCryptPasswordEncoder()) }
    @Bean fun auditorAware   (): UserAuditorAware = UserAuditorAware()

    companion object {
        private val whitelist = arrayOf(
            "/telegram/**", "/registration", "/login",
            "/actuator/**", "/password", "/password/**", "/error", "/error/**", "/guest", "/guest/**",
            "/docs/**", "/docs.yaml", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
        )
    }

}
