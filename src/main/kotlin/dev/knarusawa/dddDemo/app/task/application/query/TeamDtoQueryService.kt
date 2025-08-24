package dev.knarusawa.dddDemo.app.task.application.query

import dev.knarusawa.dddDemo.app.task.application.dto.TeamDto
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId

interface TeamDtoQueryService {
  fun findByTeamId(teamId: TeamId): TeamDto
}
