package dev.knarusawa.dddDemo.app.task.application.readModel

import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import org.springframework.data.jpa.repository.JpaRepository

interface TaskReadModelRepository : JpaRepository<TaskReadModel, TaskId> {
  fun save(task: TaskReadModel)

  fun findByTaskId(taskId: String): TaskReadModel?
}
