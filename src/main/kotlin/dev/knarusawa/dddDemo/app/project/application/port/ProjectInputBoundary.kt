package dev.knarusawa.dddDemo.app.project.application.port

import dev.knarusawa.dddDemo.app.project.application.usecase.inputData.CreateProjectInputData
import dev.knarusawa.dddDemo.app.project.application.usecase.outputData.CreateProjectOutputData

interface ProjectInputBoundary {
  fun handle(input: CreateProjectInputData): CreateProjectOutputData
}
