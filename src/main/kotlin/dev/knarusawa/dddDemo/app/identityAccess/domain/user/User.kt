package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.LoginFailed
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UserSignupCompletedEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UsernameChangedEvent
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
  @AttributeOverride(name = "value", column = Column("email_verified"))
  private var emailVerified: Boolean,
  @Embedded
  @AttributeOverride(name = "value", column = Column("given_name"))
  private var givenName: GivenName,
  @Embedded
  @AttributeOverride(name = "value", column = Column("family_name"))
  private var familyName: FamilyName,
  @Embedded
  @AttributeOverride(name = "value", column = Column("login_failure_count"))
  private var loginFailureCount: LoginFailureCount = LoginFailureCount.init(),
  @Embedded
  @AttributeOverride(name = "value", column = Column("last_login_failed_at"))
  private var lastLoginFailedAt: LastLoginFailedAt = LastLoginFailedAt.init(),
  @Enumerated(EnumType.STRING)
  @AttributeOverride(name = "value", column = Column("account_status"))
  private var accountStatus: AccountStatus,
  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,
  @Transient
  private val events: MutableList<IdentityAccessEvent> = mutableListOf(),
) {
  companion object {
    fun signup(
      username: Username,
      password: Password,
      givenName: GivenName,
      familyName: FamilyName,
    ): User {
      val user =
        User(
          userId = UserId.new(),
          username = username,
          password = password,
          emailVerified = false,
          givenName = givenName,
          familyName = familyName,
          accountStatus = AccountStatus.NORMAL,
        )
      val event = UserSignupCompletedEvent(user = user)
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
    this.loginFailureCount = LoginFailureCount.reset()
    this.lastLoginFailedAt = LastLoginFailedAt.reset()
    this.accountStatus = AccountStatus.NORMAL
  }

  fun loginFailed() {
    this.loginFailureCount = LoginFailureCount.increment(count = this.loginFailureCount)
    this.lastLoginFailedAt = LastLoginFailedAt.now()
    if (this.loginFailureCount.isLockedCount()) {
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
      UsernameChangedEvent(
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
