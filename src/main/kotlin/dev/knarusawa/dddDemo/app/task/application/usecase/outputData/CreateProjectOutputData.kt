package dev.knarusawa.dddDemo.app.task.application.usecase.outputData

import dev.knarusawa.dddDemo.app.task.adapter.controller.model.CreateProjectResponse
import dev.knarusawa.dddDemo.app.task.domain.project.Project

data class CreateProjectOutputData(
  val response: CreateProjectResponse,
) {
  companion object {
    fun of(project: Project) =
      CreateProjectOutputData(
        response =
          CreateProjectResponse(
            projectId = project.projectId.get(),
            name = project.getProjectName().get(),
          ),
      )
  }
}
