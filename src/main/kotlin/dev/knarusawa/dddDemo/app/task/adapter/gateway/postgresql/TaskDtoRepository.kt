package dev.knarusawa.dddDemo.app.task.adapter.gateway.postgresql

import dev.knarusawa.dddDemo.app.task.application.dto.TaskDto
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import org.springframework.data.jpa.repository.JpaRepository

interface TaskDtoRepository : JpaRepository<TaskDto, TaskId> {
  fun save(dto: TaskDto)

  fun findByTaskId(taskId: String): TaskDto?
}
