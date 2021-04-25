package space.colaba.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import space.colaba.model.User
import space.colaba.service.UserService
import space.colaba.security.util.TokenUtil.TOKEN_PREFIX
import space.colaba.security.util.userId
import javax.servlet.http.HttpServletRequest


@RestController @RequestMapping(path = ["/user"])
class UserController(private val userService: UserService) {
    @PostMapping @Operation(summary = "@me: Find User JWT token in `Authorization` header", security = [SecurityRequirement(name = TOKEN_PREFIX)])
    fun getUser(request: HttpServletRequest): User = userService.findOrThrow(request.userId())

    @PatchMapping("/karma") @Operation(summary = "Управление балансом, пример: -666 (вычесть), 666(начислить)")
    fun updateKarma(@RequestBody amount: Int, request: HttpServletRequest): Int {
        val email = request.userId()
        log.info("💰 Upgrading karma [$amount] for user: $email")
        return userService.updateKarma(email, amount)
    }

    companion object { private val log = LoggerFactory.getLogger(this::class.java) }
}
