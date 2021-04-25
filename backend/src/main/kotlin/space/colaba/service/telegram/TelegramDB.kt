package space.colaba.service.telegram

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import space.colaba.repository.TelegramRepository

@Service
class TelegramDB(private val repository: TelegramRepository) {
    fun findByChatId(chatId: Long): Telegram? = repository.findTelegramByChatId(chatId)
    fun findByUserId(userId: Long): Telegram? = repository.findTelegramByUserId(userId)
    fun find(email: String): Telegram? = repository.findTelegramByUserEmail(email)

    fun save(telegram: Telegram): Telegram = repository.save(telegram).apply{
        log.info("🔐 User $userEmail (tid: $userId) successfully logged in in Telegram. Chat: $chatId")
    }

    internal fun findAll(): List<Telegram> = repository.findAll()
    internal fun exists(photo: Telegram): Boolean = repository.exists(Example.of(photo))
    internal fun existsById(email: String): Boolean = repository.existsById(email)
    internal fun delete(email: String) {
        log.info("🧨 Deleting telegram by email: $email")
        repository.deleteById(email)
    }

    companion object { private val log = LoggerFactory.getLogger(this::class.java) }
}
