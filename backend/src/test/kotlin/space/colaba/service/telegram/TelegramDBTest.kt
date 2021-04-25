package space.colaba.service.telegram

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import space.colaba.IntegrationTest
import space.colaba.service.telegram.TgmStatus.STARTED
import java.time.ZonedDateTime

@IntegrationTest
internal class TelegramDBTest(@Autowired private val service: TelegramDB) {

    @Test
    fun create() {
        val email = "steklopod@gmail.com"
        assertFalse(service.existsById(email))
        val telegram = Telegram(userEmail = email, userId = 1L, chatId = 2L, loginTriggerStart = ZonedDateTime.now())
        service.save(telegram)

        println("save")
        service.save(telegram)

        with(service.find(email)) {
            assertNotNull(this!!)
            assertEquals(STARTED, status)
            assertNotNull(loginTriggerStart)
        }
        assertEquals(email, service.findByUserId(1L)!!.userEmail)
        assertEquals(email, service.findByChatId(2L)!!.userEmail)

        service.delete(email)

        assertFalse(service.existsById(email))
    }
}
