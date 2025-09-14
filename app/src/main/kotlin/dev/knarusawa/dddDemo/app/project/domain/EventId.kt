package dev.knarusawa.dddDemo.app.project.domain

import kotlinx.serialization.Serializable

@Serializable
open class EventId(
  val eventId: String,
)
