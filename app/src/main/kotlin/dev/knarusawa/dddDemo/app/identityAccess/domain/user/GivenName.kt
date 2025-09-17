package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import jakarta.persistence.Embeddable
import kotlinx.serialization.Serializable

@Embeddable
@Serializable
data class GivenName(
  private val value: String,
) {
  companion object {
    fun of(value: String) = GivenName(value = value)
  }

  fun get() = value

  override fun toString() = "*****"

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is GivenName) return false
    if (value != other.value) return false
    return true
  }

  override fun hashCode(): Int = value.hashCode()
}
