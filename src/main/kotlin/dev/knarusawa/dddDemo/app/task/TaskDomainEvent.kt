package dev.knarusawa.dddDemo.app.task

import dev.knarusawa.dddDemo.app.task.domain.EventId
import kotlinx.serialization.Serializable

@Serializable
abstract class TaskDomainEvent {
  abstract val eventId: EventId
}
