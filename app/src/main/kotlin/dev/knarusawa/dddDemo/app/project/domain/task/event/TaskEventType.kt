package dev.knarusawa.dddDemo.app.project.domain.task.event

enum class TaskEventType {
  TASK_CREATED,
  TASK_CHANGED,
  TASK_COMPLETED,
  ;

  fun toPublishedType(): dev.knarusawa.dddDemo.publishedLanguage.project.proto.TaskEventType =
    when (this) {
      TASK_CREATED -> dev.knarusawa.dddDemo.publishedLanguage.project.proto.TaskEventType.TASK_CREATED
      TASK_CHANGED -> dev.knarusawa.dddDemo.publishedLanguage.project.proto.TaskEventType.TASK_CHANGED
      TASK_COMPLETED -> dev.knarusawa.dddDemo.publishedLanguage.project.proto.TaskEventType.TASK_COMPLETED
    }
}
