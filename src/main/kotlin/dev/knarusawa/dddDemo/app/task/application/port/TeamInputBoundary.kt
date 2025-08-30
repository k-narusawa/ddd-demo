package dev.knarusawa.dddDemo.app.task.application.port

import dev.knarusawa.dddDemo.app.task.application.usecase.inputData.CreateTeamInputData
import dev.knarusawa.dddDemo.app.task.application.usecase.outputData.CreateTeamOutputData

interface TeamInputBoundary {
  fun handle(input: CreateTeamInputData): CreateTeamOutputData
}
