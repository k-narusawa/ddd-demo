package dev.knarusawa.dddDemo.app.project.domain.member.command

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.member.MembershipStatus

data class SignupMemberCommand(
  val memberId: MemberId,
  val membershipStatus: MembershipStatus,
)
