package dev.knarusawa.dddDemo.app.task.application.port

import dev.knarusawa.dddDemo.app.task.application.usecase.createTask.CreateTaskInputData
import dev.knarusawa.dddDemo.app.task.application.usecase.createTask.CreateTaskOutputData

interface TaskInputBoundary {
  fun handle(input: CreateTaskInputData): CreateTaskOutputData
}
