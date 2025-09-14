package dev.knarusawa.dddDemo.app.project.domain.member.event

import dev.knarusawa.dddDemo.app.project.domain.DomainEvent
import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.util.JsonUtil
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
sealed class MemberEvent : DomainEvent {
  abstract override val eventId: MemberEventId
  abstract val memberId: MemberId
  abstract val eventType: MemberEventType
  abstract override val occurredAt: LocalDateTime

  companion object {
    fun fromPayload(payload: String): MemberEvent =
      JsonUtil.json.decodeFromString<MemberEvent>(payload)
  }

  fun toPayload() = JsonUtil.json.encodeToString(MemberEvent.serializer(), this)
}
