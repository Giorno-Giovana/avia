package space.colaba.controller

import io.swagger.v3.oas.annotations.Operation
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import space.colaba.service.UserService
import javax.servlet.http.HttpServletResponse


@RestController @RequestMapping(path = ["/guest"])
class GuestController(private val userService: UserService) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @GetMapping("/{username}")
    @Operation(summary = "Is @username exists?")
    fun userExist(@PathVariable username: String, response: HttpServletResponse): Boolean =
        userService.userExists(username).apply {
            if (!this) log.info("Ok. New guest: [$username]")
            userService.isEnabled(username, response)
        }

}
