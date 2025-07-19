package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import jakarta.persistence.Embeddable
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder

@Embeddable
data class Password private constructor(
  val value: String,

  @Transient
  private var hashed: Boolean = false,
) {
  companion object {
    fun of(value: String): Password {
      val encoder = Argon2PasswordEncoder(
        16, // memory cost
        32, // parallelism
        1, // iterations
        64, // hash length
        32 // salt length
      )
      val hashedValue = encoder.encode(value)
      return Password(value = hashedValue, hashed = true)
    }
  }

  override fun toString(): String {
    return "********"
  }
}