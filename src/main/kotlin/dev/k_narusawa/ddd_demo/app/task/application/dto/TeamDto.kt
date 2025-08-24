package dev.k_narusawa.ddd_demo.app.task.application.dto

import dev.k_narusawa.ddd_demo.app.task.domain.team.TeamId
import dev.k_narusawa.ddd_demo.app.task.domain.team.TeamName

data class TeamDto(
    val teamId: TeamId,
    val teamName: TeamName,
)
