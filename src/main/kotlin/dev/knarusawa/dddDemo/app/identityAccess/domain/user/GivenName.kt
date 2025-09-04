package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import jakarta.persistence.Embeddable

@Embeddable
data class GivenName(
  private val value: String,
) {
  companion object {
    fun of(value: String) = GivenName(value = value)
  }

  override fun toString() = "*****"
}
