package dev.knarusawa.dddDemo.app.project.domain.task.event

import dev.knarusawa.dddDemo.publishedLanguage.project.proto.PLTaskEventType

enum class TaskEventType {
  TASK_CREATED,
  TASK_CHANGED,
  TASK_COMPLETED,
  ;

  fun toPublishedType(): PLTaskEventType =
    when (this) {
      TASK_CREATED -> PLTaskEventType.TASK_CREATED
      TASK_CHANGED -> PLTaskEventType.TASK_CHANGED
      TASK_COMPLETED -> PLTaskEventType.TASK_COMPLETED
    }
}
