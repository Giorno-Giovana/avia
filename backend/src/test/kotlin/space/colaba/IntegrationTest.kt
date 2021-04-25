package space.colaba

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@Target(AnnotationTarget.CLASS) @Retention(AnnotationRetention.RUNTIME)
@ActiveProfiles("test")
annotation class IntegrationTest
