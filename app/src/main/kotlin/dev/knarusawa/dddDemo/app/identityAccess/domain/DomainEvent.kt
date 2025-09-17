package dev.knarusawa.dddDemo.app.identityAccess.domain

import java.time.LocalDateTime

interface DomainEvent<T> {
  abstract val eventId: EventId
  abstract val occurredAt: LocalDateTime

  abstract fun serialize(): String

  abstract fun toEventMessage(): T
}
