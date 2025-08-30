package dev.knarusawa.dddDemo.app.task.application.query

import dev.knarusawa.dddDemo.app.task.application.dto.ProjectDto
import dev.knarusawa.dddDemo.app.task.domain.project.ProjectId

interface ProjectDtoQueryService {
  fun findByProjectId(projectId: ProjectId): ProjectDto
}
