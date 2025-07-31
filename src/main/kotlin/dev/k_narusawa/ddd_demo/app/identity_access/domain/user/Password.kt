package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import jakarta.persistence.Embeddable
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder

@Embeddable
data class Password private constructor(
  val value: String,

  @Transient()
  private var hashed: Boolean = true,
) {
  companion object {
    private val encoder = Argon2PasswordEncoder(
      16, // memory cost
      32, // parallelism
      100, // iterations
      64, // hash length
      32 // salt length
    )

    fun of(value: String): Password {
      val hashedValue = encoder.encode(value)
        ?: throw IllegalStateException("本来ありえないはずだけどパスワードがNullの可能性がある")
      return Password(value = hashedValue, hashed = true)
    }
  }

  fun matches(rawPassword: String): Boolean {
    return encoder.matches(rawPassword, value)
  }

  override fun toString(): String {
    return "********"
  }
}