package space.colaba.service.telegram

import com.fasterxml.jackson.annotation.JsonBackReference
import space.colaba.config.DateAudit
import space.colaba.model.User
import space.colaba.service.telegram.TgmStatus.STARTED
import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType.LAZY
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.Table


@Entity
@Table
data class Telegram(
    @Id
    var userEmail: String? = null,
    val userId: Long,
    val chatId: Long,
    @Enumerated(EnumType.STRING)
    var status: TgmStatus = STARTED,

    val loginTriggerStart: ZonedDateTime? = null,
    val isDone: Boolean = false,

    ) : DateAudit() {
    @OneToOne(fetch = LAZY) @PrimaryKeyJoinColumn(name = "user_email", referencedColumnName = "email") @JsonBackReference
    var user: User? = null
}
