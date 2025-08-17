package dev.k_narusawa.ddd_demo.app.task.application.query

import dev.k_narusawa.ddd_demo.app.task.application.dto.TeamDto
import dev.k_narusawa.ddd_demo.app.task.domain.team.TeamId

interface TeamDtoQueryService {
  fun findByTeamId(teamId: TeamId): TeamDto
}
