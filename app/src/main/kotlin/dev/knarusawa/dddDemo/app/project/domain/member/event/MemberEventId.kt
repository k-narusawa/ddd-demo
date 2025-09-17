package dev.knarusawa.dddDemo.app.project.domain.member.event

import dev.knarusawa.dddDemo.app.project.domain.EventId
import java.util.UUID

data class MemberEventId(
  private val value: String,
) : EventId(eventId = value) {
  companion object {
    fun new() = MemberEventId(value = UUID.randomUUID().toString())
  }

  fun get() = value
}
