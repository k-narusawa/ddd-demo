package dev.knarusawa.dddDemo.app.task.application.eventHandler

import dev.knarusawa.dddDemo.app.task.application.port.ReceiveMessageInputBoundary
import dev.knarusawa.dddDemo.app.task.application.readModel.TaskReadModel
import dev.knarusawa.dddDemo.app.task.application.readModel.TaskReadModelRepository
import dev.knarusawa.dddDemo.app.task.domain.task.Task
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCompleted
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReceiveMessageEventHandler(
  private val taskEventRepository: TaskEventRepository,
  private val taskReadModelRepository: TaskReadModelRepository,
) : ReceiveMessageInputBoundary {
  override fun handle(event: TaskCreated) {
    val events = taskEventRepository.findByTaskId(taskId = event.taskId)
    val task = Task.applyFromFirstEvent(events = events)
    val readModel = TaskReadModel.from(task = task)
    taskReadModelRepository.save(task = readModel)
  }

  override fun handle(event: TaskChanged) {
    val events = taskEventRepository.findByTaskId(taskId = event.taskId)
    val task = Task.applyFromFirstEvent(events = events)
    val readModel = TaskReadModel.from(task = task)
    taskReadModelRepository.save(task = readModel)
  }

  override fun handle(event: TaskCompleted) {
    val events = taskEventRepository.findByTaskId(taskId = event.taskId)
    val task = Task.applyFromFirstEvent(events = events)
    val readModel = TaskReadModel.from(task = task)
    taskReadModelRepository.save(task = readModel)
  }
}
