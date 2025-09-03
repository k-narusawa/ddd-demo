package dev.knarusawa.dddDemo.app.task.application.readModel

import dev.knarusawa.dddDemo.app.task.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import org.springframework.data.jpa.repository.JpaRepository

interface TaskReadModelQueryService : JpaRepository<TaskReadModel, TaskId> {
  fun findByTaskId(taskId: TaskId): TaskReadModel?

  fun findByProjectId(projectId: ProjectId): List<TaskReadModel>
}
