package dev.knarusawa.dddDemo.app.project.domain.project

import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectEvent

interface ProjectRepository {
  fun save(event: ProjectEvent)

  fun loadEvents(projectId: ProjectId): List<ProjectEvent>
}
