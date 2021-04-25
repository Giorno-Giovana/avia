package space.colaba.repository

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import space.colaba.IntegrationTest

@IntegrationTest
internal class UserRepositoryTest(@Autowired private val repository: UserRepository) {

    @Test
    fun firstTest() {
        val audit = repository.findUserByEmail("steklopod@gmail.com")
        with(audit) {
            assertNotNull(this!!)
        }
    }

}
