package dev.k_narusawa.ddd_demo.app.identity_access.domain.loginAttempt

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import dev.k_narusawa.ddd_demo.util.logger
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.LocalDateTime

@Entity
@Table(name = "ddd_login_attempt")
class LoginAttempt private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("user_id"))
  val userId: UserId,

  @AttributeOverride(name = "value", column = Column("failure_count"))
  private var failureCount: Int = 0,

  @AttributeOverride(name = "value", column = Column("last_attempt_timestamp"))
  private var lastAttemptTimestamp: LocalDateTime? = null,

  @AttributeOverride(name = "value", column = Column("lock_expiration_timestamp"))
  private var lockExpirationTimestamp: LocalDateTime? = null,

  @Version
  private val version: Long? = null,
) {
  companion object {
    private const val LOGIN_ATTEMPT_LIMIT = 5
    private const val LOCKOUT_DURATION_MINUTES = 5
    private val log = logger()

    fun new(userId: UserId): LoginAttempt {
      return LoginAttempt(
        userId = userId,
        failureCount = 0,
      )
    }
  }

  fun getFailureCount() = this.failureCount

  fun authenticateFailure() {
    log.info("ログイン失敗 userId: ${this.userId.get()} failureCount: ${this.failureCount}")
    if (this.lockExpirationTimestamp != null) return

    this.failureCount = this.failureCount + 1
    this.lastAttemptTimestamp = LocalDateTime.now()
    if (this.failureCount >= LOGIN_ATTEMPT_LIMIT) {
      this.lockExpirationTimestamp =
        LocalDateTime.now().plusMinutes(LOCKOUT_DURATION_MINUTES.toLong())
    }
  }

  fun authenticateSuccess() {
    this.failureCount = 0
    this.lastAttemptTimestamp = null
    this.lockExpirationTimestamp = null
  }

  fun isLocked(): Boolean {
    if (this.lockExpirationTimestamp == null) return false
    return LocalDateTime.now() <= this.lockExpirationTimestamp
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is LoginAttempt) return false

    if (userId != other.userId) return false

    return true
  }

  override fun hashCode(): Int {
    return userId.hashCode()
  }
}
