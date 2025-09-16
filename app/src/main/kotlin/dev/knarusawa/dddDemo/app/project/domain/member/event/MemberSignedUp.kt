package dev.knarusawa.dddDemo.app.project.domain.member.event

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.member.MembershipStatus
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class MemberSignedUp(
  override val eventId: MemberEventId,
  override val memberId: MemberId,
  override val eventType: MemberEventType,
  val membershipStatus: MembershipStatus,
  @Contextual
  override val occurredAt: LocalDateTime,
) : MemberEvent() {
  companion object {
    fun of(memberId: MemberId) =
      MemberSignedUp(
        eventId = MemberEventId.new(),
        memberId = memberId,
        eventType = MemberEventType.SIGNED_UP,
        membershipStatus = MembershipStatus.NORMAL,
        occurredAt = LocalDateTime.now(),
      )
  }
}
