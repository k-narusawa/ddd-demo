package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import jakarta.persistence.Embeddable

@Embeddable
data class FamilyName(
  private val value: String,
) {
  companion object {
    fun of(value: String) = FamilyName(value = value)
  }

  override fun toString() = "*****"
}
