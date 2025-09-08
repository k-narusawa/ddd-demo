package dev.knarusawa.dddDemo.app.project

import dev.knarusawa.dddDemo.app.project.domain.EventId
import kotlinx.serialization.Serializable

@Serializable
abstract class TaskDomainEvent {
  abstract val eventId: EventId
}
