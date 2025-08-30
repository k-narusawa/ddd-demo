package dev.knarusawa.dddDemo.app.task.application.port

import dev.knarusawa.dddDemo.app.task.application.usecase.inputData.ChangeTaskInputData
import dev.knarusawa.dddDemo.app.task.application.usecase.inputData.CreateTaskInputData
import dev.knarusawa.dddDemo.app.task.application.usecase.outputData.ChangeTaskOutputData
import dev.knarusawa.dddDemo.app.task.application.usecase.outputData.CreateTaskOutputData

interface TaskInputBoundary {
  fun handle(input: CreateTaskInputData): CreateTaskOutputData

  fun handle(input: ChangeTaskInputData): ChangeTaskOutputData
}
