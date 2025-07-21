package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.AuthenticationFailedEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.AuthenticationSuccessEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.ChangeUsernameEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.publisher.AuthenticationFailedEventPublisher
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.publisher.AuthenticationSuccessEventPublisher
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.publisher.ChangeUsernameEventPublisher
import dev.k_narusawa.ddd_demo.app.identity_access.exception.AuthenticationException
import jakarta.persistence.AttributeOverride
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
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

  @OneToOne(
    fetch = FetchType.EAGER,
    cascade = [CascadeType.ALL],
    orphanRemoval = true
  )
  @JoinColumn(name = "user_id")
  private var attempt: LoginAttempt? = null,

  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,
) {
  companion object {
    fun register(
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

  fun verifyPassword(
    rawPassword: String,
    userAgent: String,
    ipAddress: String
  ) {
    val isMatch = this.password.matches(rawPassword = rawPassword)
    if (attempt == null) attempt = LoginAttempt.new(userId)
    if (isMatch) {
      this.attempt = (attempt ?: LoginAttempt.new(userId = userId))
      this.attempt?.authenticateSuccess()
      val event = AuthenticationSuccessEvent(
        user = this,
        userAgent = userAgent,
        ipAddress = ipAddress
      )
      AuthenticationSuccessEventPublisher.publish(event = event)
    } else {
      this.attempt = (attempt ?: LoginAttempt.new(userId = userId))
      this.attempt?.authenticateFailure()
      val event = AuthenticationFailedEvent(
        user = this,
        userAgent = userAgent,
        ipAddress = ipAddress
      )
      AuthenticationFailedEventPublisher.publish(event = event)
      throw AuthenticationException(
        message = "認証に失敗しました",
        userId = userId
      )
    }
  }

  fun isLock(): Boolean {
    return attempt?.isLocked() ?: LoginAttempt.new(userId = userId).isLocked()
  }

  fun changeUsername(
    newUsername: Username,
    userAgent: String,
    ipAddress: String
  ) {
    this.username = newUsername

    val event = ChangeUsernameEvent(
      user = this,
      ipAddress = ipAddress,
      userAgent = userAgent
    )
    ChangeUsernameEventPublisher.publish(event = event)
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
