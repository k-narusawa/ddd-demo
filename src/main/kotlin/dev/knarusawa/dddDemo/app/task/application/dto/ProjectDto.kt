package dev.knarusawa.dddDemo.app.task.application.dto

import dev.knarusawa.dddDemo.app.task.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.task.domain.project.ProjectName

data class ProjectDto(
  val projectId: ProjectId,
  val projectName: ProjectName,
)
