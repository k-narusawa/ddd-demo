package dev.knarusawa.dddDemo.app.task.domain.member.command

import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.member.MembershipStatus

data class SignupMemberCommand(
  val memberId: MemberId,
  val membershipStatus: MembershipStatus,
)
