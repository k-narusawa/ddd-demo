package dev.knarusawa.dddDemo.app.project.domain.task.event

import dev.knarusawa.dddDemo.app.project.domain.EventId
import java.util.UUID

data class TaskEventId(
  private val value: String,
) : EventId(eventId = value) {
  companion object {
    fun new(): TaskEventId = TaskEventId(value = UUID.randomUUID().toString())

    fun from(value: String) = TaskEventId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
