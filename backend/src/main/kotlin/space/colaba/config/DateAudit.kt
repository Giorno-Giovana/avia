package space.colaba.config

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class DateAudit {

    @CreatedDate
    @Column(updatable = false)
    @JsonProperty(access = READ_ONLY)
    open var created: ZonedDateTime? = null

    @JsonIgnore @LastModifiedDate
    open var updated: ZonedDateTime? = null
}
