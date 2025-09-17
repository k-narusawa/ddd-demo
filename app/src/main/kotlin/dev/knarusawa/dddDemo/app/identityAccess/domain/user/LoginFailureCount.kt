package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import jakarta.persistence.Embeddable
import kotlinx.serialization.Serializable

@Embeddable
@Serializable
data class LoginFailureCount(
  private val value: Int,
) {
  init {
    require(value >= 0) { "ログインの失敗回数は0以上" }
  }

  companion object {
    private const val ACCOUNT_LOCK_COUNT = 5

    fun init() = LoginFailureCount(value = 0)

    fun reset() = LoginFailureCount(value = 0)

    fun increment(count: LoginFailureCount) = LoginFailureCount(value = count.value + 1)

    fun of(value: Int) = LoginFailureCount(value = value)
  }

  fun get() = this.value

  fun isLockedCount() = this.value >= ACCOUNT_LOCK_COUNT
}
