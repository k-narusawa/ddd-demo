package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.EventId
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserEventId(
  private val value: String,
) : EventId(eventId = value) {
  companion object {
    fun new(): UserEventId = UserEventId(value = UUID.randomUUID().toString())

    fun from(value: String) = UserEventId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
