package dev.knarusawa.dddDemo.app.project.domain.task

import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEvent

interface TaskRepository {
  fun save(event: TaskEvent)

  fun loadEvents(taskId: TaskId): List<TaskEvent>
}
