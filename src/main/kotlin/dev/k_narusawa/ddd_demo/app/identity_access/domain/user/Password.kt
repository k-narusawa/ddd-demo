package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import jakarta.persistence.Embeddable

@Embeddable
data class Password private constructor(
  val value: String,

  @Transient
  private var consumed: Boolean
) {
  init {
    require(value.isNotBlank()) { "Password cannot be blank." }
    require(value.length >= 8) { "Password must be at least 8 characters long." }
  }

  companion object {
    fun of(value: String): Password {
      return Password(value, false)
    }
  }

  fun get(): String {
    if (consumed) {
      throw IllegalStateException("Password has already been consumed.")
    }
    this.consumed = true
    return value
  }

  override fun toString(): String {
    return "********"
  }
}