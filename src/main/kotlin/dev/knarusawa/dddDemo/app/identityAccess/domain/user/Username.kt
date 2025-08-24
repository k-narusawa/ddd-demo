package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import jakarta.mail.internet.AddressException
import jakarta.mail.internet.InternetAddress
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class Username private constructor(
  private val value: String,
) : Serializable {
  init {
    require(value.isNotBlank()) { "Usernameは空にできません" }
    try {
      val emailAddr = InternetAddress(value)
      emailAddr.validate()
    } catch (e: AddressException) {
      throw IllegalArgumentException("メールアドレスの形式が不正です")
    }
  }

  companion object {
    fun of(value: String) = Username(value)
  }

  fun get() = value

  override fun toString() = "********"

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Username) return false

    if (other.get() != value) return false

    return true
  }

  override fun hashCode(): Int = value.hashCode()
}
