package dev.knarusawa.dddDemo.app.task.application.usecase.createTeam

import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
import dev.knarusawa.dddDemo.app.task.domain.team.TeamName

data class CreateTeamInputData(
  val actorId: ActorId,
  val teamName: TeamName,
) {
  companion object {
    fun of(
      actorId: String,
      teamName: String,
    ) = CreateTeamInputData(
      actorId = ActorId.from(value = actorId),
      teamName = TeamName.of(value = teamName),
    )
  }
}
