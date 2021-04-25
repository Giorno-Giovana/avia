package space.colaba.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.Clock
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
class DateAuditConfig {
    companion object{
        const val defaultTimeZone = "Europe/Moscow"
    }

    @Bean
    fun auditingDateTimeProvider() = DateTimeProvider {
        Optional.of(
            ZonedDateTime.now(Clock.system(ZoneId.of(TimeZone.getTimeZone(defaultTimeZone).id)))
        )
    }

}
