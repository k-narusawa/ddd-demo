package dev.knarusawa.dddDemo.app.task.application.usecase.createTeam

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
      memberId = MemberId.from(value = memberId),
      teamName = TeamName.of(value = teamName),
    )
  }
}
