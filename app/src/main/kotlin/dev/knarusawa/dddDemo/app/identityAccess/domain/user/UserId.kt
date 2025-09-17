package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import jakarta.persistence.Embeddable
import kotlinx.serialization.Serializable
import java.util.UUID

@Embeddable
@Serializable
data class UserId(
  private val value: String,
) {
  companion object {
    fun new(): UserId = UserId(value = UUID.randomUUID().toString())

    fun from(value: String) = UserId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
