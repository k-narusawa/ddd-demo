package dev.knarusawa.dddDemo.app.task.domain.task.event

import dev.knarusawa.dddDemo.app.task.domain.task.TaskId

interface TaskEventRepository {
  fun save(event: TaskEvent)

  fun findByTaskIdOrderByOccurredAtAsc(taskId: TaskId): List<TaskEvent>
}
