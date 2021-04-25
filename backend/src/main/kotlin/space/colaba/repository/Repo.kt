package space.colaba.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import space.colaba.model.Role
import space.colaba.model.RoleName
import space.colaba.model.User
import space.colaba.service.telegram.Telegram


@Repository
interface UserRepository : JpaRepository<User, String> {
    fun findUserByNickname(nickname: String): User
    fun findUserByEmail(email: String?): User?
    fun existsByEmail(email: String?): Boolean
    fun existsByNickname(email: String?): Boolean

    @Query("SELECT nickname FROM User WHERE email = :email")
    fun findNicknameByEmail(email: String): String?
    @Query("SELECT email FROM User WHERE nickname = :nickname")
    fun findEmailByNickname(nickname: String): String?

    @Query("SELECT enabled FROM User WHERE nickname = :nickname")
    fun isEnabledByNickname(nickname: String): Boolean
    @Query("SELECT enabled FROM User WHERE email = :email")
    fun isEnabled(email: String): Boolean

    @Modifying
    fun deleteByNickname(nickname: String)
}

@Repository
interface TelegramRepository : JpaRepository<Telegram, String>{
    @Transactional @Modifying @Query("INSERT INTO telegram(user_id, chat_id, login_trigger_start, user_email, status) VALUES(:userId, :chatId, now(), :email, :status)", nativeQuery = true)
    fun create(email: String, status: String, userId: Long, chatId: Long)
    fun findTelegramByUserEmail(email: String): Telegram?
    fun findTelegramByUserId(userId: Long): Telegram?
    fun findTelegramByChatId(chatId: Long): Telegram?
}

@Repository
interface RoleRepository : JpaRepository<Role, RoleName>
