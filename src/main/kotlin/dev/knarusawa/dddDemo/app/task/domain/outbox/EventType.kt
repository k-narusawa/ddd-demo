package dev.knarusawa.dddDemo.app.task.domain.outbox

enum class EventType {
  TASK_CREATED,
  TASK_CHANGED,
  TASK_COMPLETED,
}
