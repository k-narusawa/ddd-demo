package dev.knarusawa.dddDemo.app.project.application.usecase.outputData

import dev.knarusawa.dddDemo.app.project.adapter.controller.model.TaskResponse
import dev.knarusawa.dddDemo.app.project.domain.task.Task

class ChangeTaskOutputData private constructor(
  val response: TaskResponse,
) {
  companion object {
    fun of(task: Task) =
      ChangeTaskOutputData(
        response =
          TaskResponse(
            taskId = task.state!!.taskId.get(),
            projectId = task.state.projectId.get(),
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
