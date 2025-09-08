package dev.knarusawa.dddDemo.app.task.domain

import kotlinx.serialization.Serializable

@Serializable
open class EventId(
  val eventId: String,
)
