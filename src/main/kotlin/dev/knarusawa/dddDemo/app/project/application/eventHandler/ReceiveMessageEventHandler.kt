package dev.knarusawa.dddDemo.app.project.application.eventHandler

import dev.knarusawa.dddDemo.app.project.application.port.ReceiveMessageInputBoundary
import dev.knarusawa.dddDemo.app.project.application.readModel.TaskReadModel
import dev.knarusawa.dddDemo.app.project.application.readModel.TaskReadModelRepository
import dev.knarusawa.dddDemo.app.project.domain.task.Task
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskCompleted
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReceiveMessageEventHandler(
  private val taskEventRepository: TaskEventRepository,
  private val taskReadModelRepository: TaskReadModelRepository,
) : ReceiveMessageInputBoundary {
  override fun handle(event: TaskCreated) {
    val events = taskEventRepository.findByTaskIdOrderByOccurredAtAsc(taskId = event.taskId)
    val task = Task.applyFromFirstEvent(taskEvents = events)
    val readModel = TaskReadModel.from(task = task)
    taskReadModelRepository.save(task = readModel)
  }

  override fun handle(event: TaskChanged) {
    val events = taskEventRepository.findByTaskIdOrderByOccurredAtAsc(taskId = event.taskId)
    val task = Task.applyFromFirstEvent(taskEvents = events)
    val readModel = TaskReadModel.from(task = task)
    taskReadModelRepository.update(
      taskId = readModel.taskId,
      operator = readModel.operator,
      title = readModel.title,
      description = readModel.description,
      assigner = readModel.assigner,
      assignee = readModel.assignee,
      fromTime = readModel.fromTime,
      toTime = readModel.toTime,
      completed = readModel.completed,
    )
  }

  override fun handle(event: TaskCompleted) {
    val events = taskEventRepository.findByTaskIdOrderByOccurredAtAsc(taskId = event.taskId)
    val task = Task.applyFromFirstEvent(taskEvents = events)
    val readModel = TaskReadModel.from(task = task)
    taskReadModelRepository.update(
      taskId = readModel.taskId,
      operator = readModel.operator,
      title = readModel.title,
      description = readModel.description,
      assigner = readModel.assigner,
      assignee = readModel.assignee,
      fromTime = readModel.fromTime,
      toTime = readModel.toTime,
      completed = readModel.completed,
    )
  }
}
