package dev.knarusawa.dddDemo.app.task.application.usecase.createTask

import dev.knarusawa.dddDemo.app.task.adapter.controller.model.CreateTaskResponse
import dev.knarusawa.dddDemo.app.task.domain.task.Task

data class CreateTaskOutputData private constructor(
  val response: CreateTaskResponse,
) {
  companion object {
    fun of(task: Task) =
      CreateTaskOutputData(
        response =
          CreateTaskResponse(
            taskId = task.state!!.taskId.get(),
            teamId = task.state.teamId.get(),
            operator = task.state.operator.get(),
            title = task.state.title.get(),
            description = task.state.description?.get(),
            assigner = task.state.assigner?.get(),
            assignee = task.state.assignee?.get(),
            fromTime = task.state.fromTime?.get(),
            toTime = task.state.toTime?.get(),
          ),
      )
  }
}
