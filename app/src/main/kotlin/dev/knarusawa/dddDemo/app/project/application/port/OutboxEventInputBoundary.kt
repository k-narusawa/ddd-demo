package dev.knarusawa.dddDemo.app.project.application.port

import dev.knarusawa.dddDemo.app.project.application.eventHandler.event.OutboxUpdatedEvent

interface OutboxEventInputBoundary {
  fun handle(event: OutboxUpdatedEvent)
}
