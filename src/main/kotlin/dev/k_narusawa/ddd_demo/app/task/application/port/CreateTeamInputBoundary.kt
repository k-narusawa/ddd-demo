package dev.k_narusawa.ddd_demo.app.task.application.port

import dev.k_narusawa.ddd_demo.app.task.application.usecase.createTeam.CreateTeamInputData
import dev.k_narusawa.ddd_demo.app.task.application.usecase.createTeam.CreateTeamOutputData

interface CreateTeamInputBoundary {
  suspend fun handle(input: CreateTeamInputData): CreateTeamOutputData
}
