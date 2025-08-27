package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import jakarta.persistence.Embeddable

@Embeddable
data class LoginFailureCount private constructor(
  private val value: Int,
) {
  companion object {
    private const val ACCOUNT_LOCK_COUNT = 5

    fun init() = LoginFailureCount(value = 0)

    fun reset() = LoginFailureCount(value = 0)

    fun increment(count: LoginFailureCount) = LoginFailureCount(value = count.value + 1)
  }

  fun isLockedCount() = this.value >= ACCOUNT_LOCK_COUNT
}
