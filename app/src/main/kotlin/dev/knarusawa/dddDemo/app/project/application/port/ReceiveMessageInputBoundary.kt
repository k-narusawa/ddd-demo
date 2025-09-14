package dev.knarusawa.dddDemo.app.project.application.port

import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskCompleted
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskCreated

interface ReceiveMessageInputBoundary {
  fun handle(event: TaskCreated)

  fun handle(event: TaskChanged)

  fun handle(event: TaskCompleted)
}
