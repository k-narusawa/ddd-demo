package dev.knarusawa.dddDemo.app.task.application.usecase.outputData

import dev.knarusawa.dddDemo.app.task.adapter.controller.model.CreateTeamResponse
import dev.knarusawa.dddDemo.app.task.domain.team.Team

data class CreateTeamOutputData(
  val response: CreateTeamResponse,
) {
  companion object {
    fun of(team: Team) =
      CreateTeamOutputData(
        response =
          CreateTeamResponse(
            teamId = team.teamId.get(),
            name = team.getTeamName().get(),
          ),
      )
  }
}
