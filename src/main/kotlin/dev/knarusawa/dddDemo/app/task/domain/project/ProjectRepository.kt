package dev.knarusawa.dddDemo.app.task.domain.project

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, ProjectId> {
  fun save(project: Project)

  fun findByProjectId(projectId: ProjectId): Project?
}
