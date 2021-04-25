package space.colaba.config

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URL
import javax.annotation.PostConstruct


var domain = "localhost"
var frontendMainPage = URL("https://$domain")
@Configuration @ConfigurationPropertiesScan
class AppInfoConfig(private val springApplicationInfo: SpringApplicationInfo) {
    @Bean fun hibernate5Module(): Module = Hibernate5Module()
    @PostConstruct fun readApplicationProperties() {
        springApplicationInfo.domain?.let { domain = it }; frontendMainPage = URL("https://$domain")
    }
}
@ConstructorBinding @ConfigurationProperties(prefix = "spring.application")
data class SpringApplicationInfo(val domain: String?)
