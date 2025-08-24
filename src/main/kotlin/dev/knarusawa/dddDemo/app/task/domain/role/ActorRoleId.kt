package dev.knarusawa.dddDemo.app.task.domain.role

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
data class ActorRoleId(
  private val value: String,
) : Serializable {
  companion object {
    fun new(): ActorRoleId = ActorRoleId(value = UUID.randomUUID().toString())

    fun from(value: String) = ActorRoleId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
