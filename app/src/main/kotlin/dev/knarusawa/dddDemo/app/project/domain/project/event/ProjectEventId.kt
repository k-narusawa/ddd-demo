package dev.knarusawa.dddDemo.app.project.domain.project.event

import dev.knarusawa.dddDemo.app.project.domain.EventId
import java.util.UUID

data class ProjectEventId(
  private val value: String,
) : EventId(eventId = value) {
  companion object {
    fun new(): ProjectEventId = ProjectEventId(value = UUID.randomUUID().toString())

    fun from(value: String) = ProjectEventId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
