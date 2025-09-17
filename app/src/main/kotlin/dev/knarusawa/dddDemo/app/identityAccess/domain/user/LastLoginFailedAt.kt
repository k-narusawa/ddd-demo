package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import jakarta.persistence.Embeddable
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Embeddable
@Serializable
data class LastLoginFailedAt(
  @Contextual
  private val value: LocalDateTime?,
) {
  init {
    require(value == null || value.isBefore(LocalDateTime.now())) {
      "最終ログイン失敗時刻は現時刻以前"
    }
  }

  companion object {
    fun init() = LastLoginFailedAt(value = null)

    fun reset() = LastLoginFailedAt(value = null)

    fun now() = LastLoginFailedAt(value = LocalDateTime.now())
  }

  fun get() = this.value
}
