package dev.k_narusawa.ddd_demo.app.task.domain.actor

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
data class ActorId(
  private val value: String
) : Serializable {
  companion object {
    fun new(): ActorId {
      return ActorId(value = UUID.randomUUID().toString())
    }

    fun from(value: String) = ActorId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
