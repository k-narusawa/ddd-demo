package dev.knarusawa.dddDemo.app.task.application.port

import dev.knarusawa.dddDemo.app.task.application.usecase.createTeam.CreateTeamInputData
import dev.knarusawa.dddDemo.app.task.application.usecase.createTeam.CreateTeamOutputData

interface TeamInputBoundary {
  fun handle(input: CreateTeamInputData): CreateTeamOutputData
}
