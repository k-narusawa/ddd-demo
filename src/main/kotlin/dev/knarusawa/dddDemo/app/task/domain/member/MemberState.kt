package dev.knarusawa.dddDemo.app.task.domain.member

import dev.knarusawa.dddDemo.app.task.domain.member.command.SignupMemberCommand

class MemberState private constructor(
  val memberId: MemberId,
  membershipStatus: MembershipStatus,
  version: Long,
) {
  var membershipStatus = membershipStatus
    private set
  var version = version
    private set

  companion object {
    fun init(cmd: SignupMemberCommand) =
      MemberState(
        memberId = cmd.memberId,
        membershipStatus = cmd.membershipStatus,
        version = 1,
      )
  }
}
