package dev.knarusawa.dddDemo.app.project.domain

import java.time.LocalDateTime

interface DomainEvent {
  abstract val eventId: EventId
  abstract val occurredAt: LocalDateTime
}
