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
      require(value = value.isNotBlank()) { "Password can not be blank." }
      require(value = value.length >= 8) { "Password more than 8 words." }

      val hashedValue = encoder.encode(value)
        ?: throw IllegalStateException("本来ありえないはずだけどパスワードがNullの可能性がある")
      return Password(value = hashedValue, hashed = true)
    }
  }

  fun matches(rawPassword: String): Boolean {
    return encoder.matches(rawPassword, value)
  }

  override fun toString() = "********"

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Password) return false

    if (this.value === other.value) return true
    if (this.hashed === other.hashed) return true
    return false
  }

  override fun hashCode(): Int {
    return this.hashed.hashCode()
  }
}
