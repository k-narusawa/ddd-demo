package dev.knarusawa.dddDemo.app.project.application.port

import dev.knarusawa.dddDemo.app.project.application.usecase.inputData.ChangeTaskInputData
import dev.knarusawa.dddDemo.app.project.application.usecase.inputData.CreateTaskInputData
import dev.knarusawa.dddDemo.app.project.application.usecase.outputData.ChangeTaskOutputData
import dev.knarusawa.dddDemo.app.project.application.usecase.outputData.CreateTaskOutputData

interface TaskInputBoundary {
  fun handle(input: CreateTaskInputData): CreateTaskOutputData

  fun handle(input: ChangeTaskInputData): ChangeTaskOutputData
}
