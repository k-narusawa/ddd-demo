package dev.knarusawa.dddDemo.app.task.application.dto

import dev.knarusawa.dddDemo.app.task.domain.team.TeamId
import dev.knarusawa.dddDemo.app.task.domain.team.TeamName

data class TeamDto(
  val teamId: TeamId,
  val teamName: TeamName,
)
