package dev.knarusawa.dddDemo.app.project.domain.member.event

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.member.MembershipStatus
import kotlinx.serialization.Serializable

@Serializable
data class MemberSignedUp(
  override val eventId: MemberEventId,
  override val memberId: MemberId,
  val type: MemberEventType,
  val membershipStatus: MembershipStatus,
) : MemberEvent() {
  companion object {
    fun of(memberId: MemberId) =
      MemberSignedUp(
        eventId = MemberEventId.new(),
        memberId = memberId,
        type = MemberEventType.SIGNED_UP,
        membershipStatus = MembershipStatus.NORMAL,
      )
  }
}
