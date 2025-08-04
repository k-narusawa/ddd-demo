package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class Username private constructor(
  private val value: String
) : Serializable {
  init {
    require(value.isNotBlank()) { "Username cannot be blank." }
  }

  companion object {
    fun of(value: String): Username {
      return Username(value)
    }
  }

  fun get() = value

  override fun toString() = "********"
}
