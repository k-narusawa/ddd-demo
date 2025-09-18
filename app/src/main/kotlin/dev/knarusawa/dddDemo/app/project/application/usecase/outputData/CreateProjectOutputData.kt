package dev.knarusawa.dddDemo.app.project.application.usecase.outputData

import dev.knarusawa.dddDemo.app.project.adapter.controller.model.CreateProjectResponse
import dev.knarusawa.dddDemo.app.project.domain.project.Project

data class CreateProjectOutputData(
  val response: CreateProjectResponse,
) {
  companion object {
    fun of(project: Project) =
      CreateProjectOutputData(
        response =
          CreateProjectResponse(
            projectId = project.projectId.get(),
            name = project.projectName.get(),
          ),
      )
  }
}
