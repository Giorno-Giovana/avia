package space.colaba.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import space.colaba.IntegrationTest

@IntegrationTest
internal class UserServiceTest(@Autowired private val service: UserService) {
    private val email = "steklopod@gmail.com"

    @Test
    fun tokenTest() {
        val user = service.findOrThrow(email)
        System.err.println(
            user.makeJwtToken()
        )
    }

    @Test
    fun karmaTest() {
        val user = service.findOrThrow(email)
        val karma = user.karma
        assertTrue(karma > 9)

        service.updateKarma(email, -10)
        assertTrue(service.findOrThrow(email).karma < karma)

        service.updateKarma(email, 10)
        assertEquals(karma, service.findOrThrow(email).karma)
    }

}
