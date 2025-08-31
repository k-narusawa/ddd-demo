package dev.knarusawa.dddDemo.app.task.application.port

import dev.knarusawa.dddDemo.app.task.application.eventHandler.event.OutboxEvent

interface OutboxEventInputBoundary {
  fun handle(event: OutboxEvent)
}
