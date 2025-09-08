package dev.knarusawa.dddDemo.app.project.domain.member.event

import dev.knarusawa.dddDemo.app.project.TaskDomainEvent
import dev.knarusawa.dddDemo.app.project.domain.EventId
import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.util.JsonUtil
import kotlinx.serialization.Serializable

@Serializable
sealed class MemberEvent : TaskDomainEvent() {
  abstract override val eventId: EventId
  abstract val memberId: MemberId

  companion object {
    fun fromPayload(payload: String): MemberEvent =
      JsonUtil.json.decodeFromString<MemberEvent>(payload)
  }

  fun toPayload() = JsonUtil.json.encodeToString(MemberEvent.serializer(), this)
}
