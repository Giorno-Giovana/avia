package space.colaba.security.util

import org.springframework.data.domain.AuditorAware
import java.util.Optional

class UserAuditorAware : AuditorAware<String> {
    override fun getCurrentAuditor() = Optional.of("system")
}
