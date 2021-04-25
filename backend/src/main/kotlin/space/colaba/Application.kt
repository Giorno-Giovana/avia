package space.colaba

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableCaching @EnableScheduling
@ConfigurationPropertiesScan
@OpenAPIDefinition(servers = [Server(url = "https://colaba.space/api/")], info = Info(description = "API", title = "BACKEND", version = "1.0"))
class Application
fun main(args: Array<String>) { runApplication<Application>(*args) }
