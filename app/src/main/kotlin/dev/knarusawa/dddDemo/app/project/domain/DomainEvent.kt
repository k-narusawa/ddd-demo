package dev.knarusawa.dddDemo.app.project.domain

import java.time.LocalDateTime

interface DomainEvent<T> {
  abstract val eventId: EventId
  abstract val occurredAt: LocalDateTime

  abstract fun toPublishedLanguage(): T
}
