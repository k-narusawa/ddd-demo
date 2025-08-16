package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import dev.k_narusawa.ddd_demo.app.identity_access.domain.DomainEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.exception.LoginFailed
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.UsernameChangedEvent
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@Table(name = "ddd_user")
class User private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("user_id"))
  val userId: UserId,

  @Embedded
  @AttributeOverride(name = "value", column = Column("username"))
  private var username: Username,

  @Embedded
  @AttributeOverride(name = "value", column = Column("password"))
  private var password: Password,

  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,

  @Transient
  private val events: MutableList<DomainEvent> = mutableListOf()
) {
  companion object {
    fun signup(
      username: Username,
      password: Password
    ): User {
      return User(
        userId = UserId.new(),
        username = username,
        password = password
      )
    }
  }

  fun getUsername() = this.username
  fun getEvents() = this.events.toList()

  fun verifyPassword(rawPassword: String) {
    val isMatch = this.password.matches(rawPassword = rawPassword)
    if (!isMatch) {
      throw LoginFailed(
        message = "認証に失敗しました",
        userId = userId
      )
    }
  }

  fun changeUsername(
    newUsername: Username,
    userAgent: String,
    ipAddress: String
  ) {
    this.username = newUsername

    val event = UsernameChangedEvent(
      user = this,
      ipAddress = ipAddress,
      userAgent = userAgent
    )
    events.add(element = event)
  }

  fun changePassword(
    newPassword: Password,
  ) {
    this.password = newPassword
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is User) return false

    if (userId != other.userId) return false

    return true
  }

  override fun hashCode(): Int {
    return userId.hashCode()
  }
}
