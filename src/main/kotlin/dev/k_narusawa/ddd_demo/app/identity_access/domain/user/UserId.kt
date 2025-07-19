package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
data class UserId(
  private val value: String
) : Serializable {
  companion object {
    fun new(): UserId {
      return UserId(value = UUID.randomUUID().toString())
    }
  }

  fun get() = value

  override fun toString() = value
}
