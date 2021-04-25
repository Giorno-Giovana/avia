package space.colaba.service

import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Example
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service
import space.colaba.config.ApiException.NotFoundException
import space.colaba.model.User
import space.colaba.model.UserPrincipal
import space.colaba.repository.UserRepository
import java.util.*
import javax.servlet.http.HttpServletResponse

@Service
class UserService(private val repository: UserRepository): UserDetailsManager {

    fun find(email: String) = repository.findUserByEmail(email)
    fun save(user: User): User = repository.save(user).apply { log.info("🤷 saving new user: $user") }

    override fun createUser(userDetails: UserDetails) { userDetails.createUser() }

    fun createUser(u: UserPrincipal) = User(
        nickname = u.nickname, password = u.password,
        email = u.email, firstName = u.firstName, lastName = u.lastName
    ).createUser()

    private fun UserDetails.createUser(): User {
        notExistsOrThrow(username)
        return saveOrUpdate(
            User(email = username, password = BCryptPasswordEncoder().encode(password.trim()))
        )
    }

    override fun userExists(email: String?): Boolean = repository.existsByEmail(email?.lowercase(Locale.getDefault())?.trim()).apply { if (!this) log.info("OK: username [$email] is free for registration (not exists)") }
    fun notExistsOrThrow(email: String) { if (userExists(email)) throw RuntimeException("USER_EXIST") }
    fun userExistsByNickname(nickname: String): Boolean = repository.existsByNickname(nickname)
    fun isEnabled(email: String): Boolean = isNew("email: [$email]") { repository.isEnabled(email.lowercase(Locale.getDefault()).trim()) }
    fun isEnabled(email: String, response: HttpServletResponse): Boolean = isEnabled(email).notAllowedStatus(response)
    fun isEnabledByNickname(nickname: String): Boolean = isNew("nickname: [$nickname]") { repository.isEnabledByNickname(nickname) }

    override fun loadUserByUsername(email: String?): User? = repository.findUserByEmail(email?.lowercase(Locale.getDefault())?.trim())
        ?.apply { if (!isEnabled) throw RuntimeException("USER_BLOCKED") }
    fun loadUserByUsernameOrThrow(email: String): User = loadUserByUsername(email) ?: throw NotFoundException("User not found: $email")

    fun findNicknameByEmail(email: String): String? = repository.findNicknameByEmail(email)
    fun findEmailByNickname(nickname: String): String? = repository.findEmailByNickname(nickname)
    fun findOrThrow(email: String): User = try {
         find(email) ?: throw NotFoundException("USER not found for: $email")
    } catch (e: Exception) {
        throw RuntimeException("User with email: $email")
    }.apply { if (!isEnabled) throw RuntimeException("USER_BLOCKED") }

    fun findNickname(email: String): String? = repository.findNicknameByEmail(email)

    fun saveOrUpdate(user: User): User = repository.save(user)

    override fun changePassword(oldPassword: String, newPassword: String) = println("Password changed")
    fun changePassword(nickname: String, oldPassword: String, newPassword: String): User = findOrThrow(nickname).updatePassword(oldPassword, newPassword)
    private fun User.updatePassword(oldPassword: String, newPassword: String): User = saveOrUpdate(validatePassword(oldPassword).encodePassword(newPassword))

    override fun deleteUser(username: String) = repository.deleteByNickname(username)
    override fun updateUser(userDetails: UserDetails) {
        saveOrUpdate(userDetails as User)
    }

    fun Boolean.notAllowedStatus(response: HttpServletResponse) = apply { if (!this) response.status = HttpStatus.METHOD_NOT_ALLOWED.value() }

    fun isNew(msg: String, function: () -> Boolean): Boolean = try {
        function()
    } catch (e: EmptyResultDataAccessException) {
        true
    }.apply { if (!this) log.warn("${HttpStatus.METHOD_NOT_ALLOWED.reasonPhrase}: $msg is blocked!") }

    fun updateKarmaAndTelegram(email: String, amount: Int = 15)  = findOrThrow(email).let {
        val newKarma = it.karma + amount
        save(it.copy(karma = newKarma) )
        log.info("💸 TELEGRAM achieved {$amount 🔮}: $newKarma for user [$email]")
        newKarma
    }

    fun updateKarma(email: String, amount: Int = 15) = findOrThrow(email).let {
        val newKarma = it.karma + amount
        save(it.copy(karma = newKarma))
        log.info("💸💵 +++ new karma: $newKarma for user [$email]")
        newKarma
    }

    internal fun exists(user: User): Boolean = repository.exists(Example.of(user))
    internal fun existsByEmail(email: String): Boolean = repository.existsByEmail(email)
    internal fun findAll(): List<User> = repository.findAll()
    internal fun deleteByEmail(email: String) {
        log.info("🤷 Deleting user by email: $email")
        repository.deleteById(email)
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

}
