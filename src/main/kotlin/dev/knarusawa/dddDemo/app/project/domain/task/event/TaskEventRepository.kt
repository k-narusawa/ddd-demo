package dev.knarusawa.dddDemo.app.project.domain.task.event

import dev.knarusawa.dddDemo.app.project.domain.task.TaskId

interface TaskEventRepository {
  fun save(taskEvent: TaskEvent)

  fun findByTaskIdOrderByOccurredAtAsc(taskId: TaskId): List<TaskEvent>
}
