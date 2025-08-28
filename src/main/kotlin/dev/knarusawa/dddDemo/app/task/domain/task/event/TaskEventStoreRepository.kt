package dev.knarusawa.dddDemo.app.task.domain.task.event

import dev.knarusawa.dddDemo.app.task.domain.task.TaskId

interface TaskEventStoreRepository {
  fun commit(event: TaskEvent)

  fun loadEvent(taskId: TaskId): List<TaskEvent>
}
