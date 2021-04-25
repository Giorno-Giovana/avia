package space.colaba.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader.SCOPE_KEY
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import space.colaba.config.domain
import space.colaba.model.RoleName.GUEST
import space.colaba.security.util.TokenUtil
import space.colaba.service.telegram.Telegram
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType.EAGER
import javax.persistence.FetchType.LAZY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToOne
import javax.persistence.Table


@Entity @Table
data class User(
    @Id
    var email   : String,
    var nickname: String? = null,

    @JsonProperty(access = WRITE_ONLY)
    private var password: String = BCryptPasswordEncoder().encode("test"),

    val firstName: String? = null,
    val lastName : String? = null,
    val karma    : Int     = 0,

    @JsonIgnore
    val enabled: Boolean = true,

) : UserDetails {
    @OneToOne(mappedBy = "user", fetch = LAZY) @JoinColumn(name = "user_email") @JsonManagedReference
    val telegram: Telegram? = null

    @ManyToMany(fetch = EAGER) @JoinTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_email", referencedColumnName = "email")], inverseJoinColumns = [JoinColumn(name = "role", referencedColumnName = "role")])
    val scope  : MutableSet<Role> = mutableSetOf(Role())

    val fullName : String?
        get() = lastName?.let { "$firstName $it" } ?: firstName

    @JsonProperty("scope")
    override fun getAuthorities() = scope.map { it.role }
    @JsonIgnore override fun getUsername() = email
    @JsonIgnore override fun getPassword() = password
    @JsonIgnore override fun isEnabled() = enabled
    @JsonIgnore override fun isAccountNonLocked() = enabled
    @JsonIgnore override fun isAccountNonExpired() = enabled
    @JsonIgnore override fun isCredentialsNonExpired() = enabled

    fun encodePassword(newPassword: String = password): User = copy(password = BCryptPasswordEncoder().encode(newPassword))
    fun validatePassword(oldPassword: String): User { if (!BCryptPasswordEncoder().matches(oldPassword, password)) throw RuntimeException("PASSWORD_NOT_MATCH"); return this }

    fun makeJwtToken(): String = Jwts.builder()
        .claim(SCOPE_KEY, authorities.toString())
        .setAudience(username)
        .setSubject(username)
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date(Date().time + TimeUnit.DAYS.toMillis(666)))
        .signWith(SignatureAlgorithm.HS512, TokenUtil.fbi)
        .setIssuer(domain)
        .compact()
}


@Table @Entity
data class Role(@Id @Enumerated(EnumType.STRING) val role: RoleName = GUEST) {
    @JsonIgnore @ManyToMany(mappedBy = "scope")
    lateinit var users: MutableSet<User>
}

enum class RoleName : GrantedAuthority {
    GUEST, USER, TEST, ADMIN, ;
    override fun getAuthority(): String = name
}

data class UserPrincipal(
    val email     : String,
    val password  : String,
    val nickname  : String? = null,
    val firstName : String? = null,
    val lastName  : String? = null,
    val timezone  : String? = null,
    val locale    : String? = null,
)
