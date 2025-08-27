package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
data class LastLoginFailedAt private constructor(
  private val value: LocalDateTime?,
) {
  companion object {
    fun init() = LastLoginFailedAt(value = null)

    fun reset() = LastLoginFailedAt(value = null)

    fun now() = LastLoginFailedAt(value = LocalDateTime.now())
  }
}
