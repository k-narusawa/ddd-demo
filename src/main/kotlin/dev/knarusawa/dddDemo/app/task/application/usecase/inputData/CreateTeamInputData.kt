package dev.knarusawa.dddDemo.app.task.application.usecase.inputData

import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.team.TeamName

data class CreateTeamInputData(
  val memberId: MemberId,
  val teamName: TeamName,
) {
  companion object {
    fun of(
      memberId: String,
      teamName: String,
    ) = CreateTeamInputData(
      memberId = MemberId.Companion.from(value = memberId),
      teamName = TeamName.Companion.of(value = teamName),
    )
  }
}
