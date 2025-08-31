package dev.knarusawa.dddDemo.app.task.domain.outbox

import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
data class EventId(
  private val value: String,
) {
  companion object {
    fun new(): EventId = EventId(value = UUID.randomUUID().toString())

    fun from(value: String) = EventId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
