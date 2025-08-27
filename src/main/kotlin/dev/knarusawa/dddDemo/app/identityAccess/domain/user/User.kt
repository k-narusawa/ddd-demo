package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessDomainEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.LoginFailed
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UserSignupCompletedDomainEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UsernameChangedDomainEvent
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.LocalDateTime

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
  @AttributeOverride(name = "value", column = Column("login_failure_count"))
  private var loginFailureCount: Int = 0,
  @AttributeOverride(name = "value", column = Column("last_login_failed_at"))
  private var lastLoginFailedAt: LocalDateTime? = null,
  @Enumerated(EnumType.STRING)
  @AttributeOverride(name = "value", column = Column("account_status"))
  private var accountStatus: AccountStatus,
  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,
  @Transient
  private val events: MutableList<IdentityAccessDomainEvent> = mutableListOf(),
) {
  companion object {
    fun signup(
      username: Username,
      password: Password,
      personalName: String,
    ): User {
      val user =
        User(
          userId = UserId.new(),
          username = username,
          password = password,
          accountStatus = AccountStatus.NORMAL,
        )
      val event = UserSignupCompletedDomainEvent(user = user, personalName = personalName)
      user.events.add(event)
      return user
    }
  }

  fun getUsername() = this.username

  fun getEvents() = this.events.toList()

  fun isLocked() = this.accountStatus === AccountStatus.ACCOUNT_LOCK

  fun verifyPassword(rawPassword: String) {
    val isMatch = this.password.matches(rawPassword = rawPassword)
    if (!isMatch) {
      throw LoginFailed(
        message = "認証に失敗しました",
        userId = userId,
      )
    }
  }

  fun unlock() {
    this.loginFailureCount = 0
    this.lastLoginFailedAt = null
    this.accountStatus = AccountStatus.NORMAL
  }

  fun loginFailed() {
    this.loginFailureCount = this.loginFailureCount + 1
    this.lastLoginFailedAt = LocalDateTime.now()
    if (this.loginFailureCount >= 5) {
      this.accountStatus = AccountStatus.ACCOUNT_LOCK
    }
  }

  fun changeUsername(
    newUsername: Username,
    userAgent: String,
    ipAddress: String,
  ) {
    this.username = newUsername

    val event =
      UsernameChangedDomainEvent(
        user = this,
        ipAddress = ipAddress,
        userAgent = userAgent,
      )
    events.add(element = event)
  }

  fun changePassword(newPassword: Password) {
    this.password = newPassword
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is User) return false

    if (userId != other.userId) return false

    return true
  }

  override fun hashCode(): Int = userId.hashCode()
}
