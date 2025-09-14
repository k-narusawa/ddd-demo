package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
data class UserId(
  private val value: String,
) : Serializable {
  companion object {
    fun new(): UserId = UserId(value = UUID.randomUUID().toString())

    fun from(value: String) = UserId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
