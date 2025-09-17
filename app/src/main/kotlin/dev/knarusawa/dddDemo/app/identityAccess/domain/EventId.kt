package dev.knarusawa.dddDemo.app.identityAccess.domain

import jakarta.persistence.Embeddable
import kotlinx.serialization.Serializable

@Serializable
@Embeddable
open class EventId(
  val eventId: String,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as EventId
    return eventId == other.eventId
  }

  override fun hashCode(): Int = eventId.hashCode()
}
