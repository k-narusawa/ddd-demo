package dev.knarusawa.dddDemo.app.project.application.dto

import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectName

data class ProjectDto(
  val projectId: ProjectId,
  val projectName: ProjectName,
)
