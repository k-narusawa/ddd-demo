package dev.knarusawa.dddDemo.app.task.domain.team

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
data class TeamId(
  private val value: String,
) : Serializable {
  companion object {
    fun new(): TeamId = TeamId(value = UUID.randomUUID().toString())

    fun from(value: String) = TeamId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
