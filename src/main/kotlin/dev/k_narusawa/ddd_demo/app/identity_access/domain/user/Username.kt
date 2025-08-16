package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import jakarta.persistence.Embeddable
import java.io.Serializable
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.AddressException

@Embeddable
data class Username private constructor(
  private val value: String
) : Serializable {
  init {
    require(value.isNotBlank()) { "Username cannot be blank." }
    try {
      val emailAddr = InternetAddress(value)
      emailAddr.validate()
    } catch (e: AddressException) {
      throw IllegalArgumentException("Invalid email address format.")
    }
  }

  companion object {
    fun of(value: String): Username {
      return Username(value)
    }
  }

  fun get() = value

  override fun toString() = "********"

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Username) return false

    if (other.get() != value) return false

    return true
  }

  override fun hashCode(): Int {
    return value.hashCode()
  }
}
