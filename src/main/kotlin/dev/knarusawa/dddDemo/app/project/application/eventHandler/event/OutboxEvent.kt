package dev.knarusawa.dddDemo.app.project.application.eventHandler.event

import dev.knarusawa.dddDemo.app.project.domain.outbox.EventId

data class OutboxEvent private constructor(
  val eventId: EventId?,
) {
  companion object {
    fun of(payload: String?) =
      OutboxEvent(
        eventId = payload?.let { EventId.from(value = payload) },
      )
  }
}
