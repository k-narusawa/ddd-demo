package dev.knarusawa.dddDemo.app.project.application.port

import dev.knarusawa.dddDemo.app.project.application.eventHandler.event.OutboxEvent

interface OutboxEventInputBoundary {
  fun handle(event: OutboxEvent)
}
