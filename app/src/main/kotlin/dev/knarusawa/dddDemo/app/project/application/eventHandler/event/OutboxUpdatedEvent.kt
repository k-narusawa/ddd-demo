package dev.knarusawa.dddDemo.app.project.application.eventHandler.event

data class OutboxUpdatedEvent private constructor(
  val eventId: String?,
) {
  companion object {
    fun of(eventId: String?) = OutboxUpdatedEvent(eventId = eventId)
  }
}
