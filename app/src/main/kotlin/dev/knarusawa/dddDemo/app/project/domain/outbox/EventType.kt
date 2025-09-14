package dev.knarusawa.dddDemo.app.project.domain.outbox

enum class EventType {
  TASK_CREATED,
  TASK_CHANGED,
  TASK_COMPLETED,
}
