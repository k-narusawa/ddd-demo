package dev.knarusawa.dddDemo.app.task.application.port

import dev.knarusawa.dddDemo.app.task.application.usecase.inputData.CreateProjectInputData
import dev.knarusawa.dddDemo.app.task.application.usecase.outputData.CreateProjectOutputData

interface ProjectInputBoundary {
  fun handle(input: CreateProjectInputData): CreateProjectOutputData
}
