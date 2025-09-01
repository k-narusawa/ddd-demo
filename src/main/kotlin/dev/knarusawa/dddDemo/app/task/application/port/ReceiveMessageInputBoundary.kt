package dev.knarusawa.dddDemo.app.task.application.port

import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCompleted
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCreated

interface ReceiveMessageInputBoundary {
  fun handle(event: TaskCreated)

  fun handle(event: TaskChanged)

  fun handle(event: TaskCompleted)
}
