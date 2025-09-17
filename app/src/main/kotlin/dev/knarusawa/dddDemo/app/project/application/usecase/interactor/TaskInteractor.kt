package dev.knarusawa.dddDemo.app.project.application.usecase.interactor

import dev.knarusawa.dddDemo.app.project.application.port.TaskInputBoundary
import dev.knarusawa.dddDemo.app.project.application.usecase.inputData.ChangeTaskInputData
import dev.knarusawa.dddDemo.app.project.application.usecase.inputData.CreateTaskInputData
import dev.knarusawa.dddDemo.app.project.application.usecase.outputData.ChangeTaskOutputData
import dev.knarusawa.dddDemo.app.project.application.usecase.outputData.CreateTaskOutputData
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectRepository
import dev.knarusawa.dddDemo.app.project.domain.task.Task
import dev.knarusawa.dddDemo.app.project.domain.task.TaskRepository
import dev.knarusawa.dddDemo.app.project.domain.task.command.ChangeTaskCommand
import dev.knarusawa.dddDemo.app.project.domain.task.command.CreateTaskCommand
import org.springframework.stereotype.Service

@Service
class TaskInteractor(
  private val taskRepository: TaskRepository,
  private val projectRepository: ProjectRepository,
) : TaskInputBoundary {
  override fun handle(input: CreateTaskInputData): CreateTaskOutputData {
    val project =
      projectRepository.findByProjectId(projectId = input.projectId)
        ?: throw RuntimeException()

    if (!project.hasWriteRole(input.operator)) {
      throw RuntimeException() // TODO: 専用の例外クラスを作成する
    }

    val cmd =
      CreateTaskCommand(
        projectId = input.projectId,
        operator = input.operator,
        title = input.title,
        description = input.description,
        assigner = input.assigner,
        assignee = input.assignee,
        fromTime = input.fromTime,
        toTime = input.toTime,
      )

    val task = Task.create(cmd = cmd)
    task.getEvents().forEach {
      taskRepository.save(event = it)
    }

    return CreateTaskOutputData.of(task = task)
  }

  override fun handle(input: ChangeTaskInputData): ChangeTaskOutputData {
    val project =
      projectRepository.findByProjectId(projectId = input.projectId)
        ?: throw RuntimeException()

    if (!project.hasWriteRole(input.operator)) {
      throw RuntimeException() // TODO: 専用の例外クラスを作成する
    }

    val pastEvents = taskRepository.loadEvents(taskId = input.taskId)
    val task = Task.from(pastEvents = pastEvents)

    val cmd =
      ChangeTaskCommand(
        taskId = input.taskId,
        operator = input.operator,
        title = input.title,
        description = input.description,
        assigner = input.assigner,
        assignee = input.assignee,
        fromTime = input.fromTime,
        toTime = input.toTime,
      )
    val newEvents = task.handle(cmd = cmd)
    newEvents.forEach { event ->
      task.apply(event = event)
    }

    task.getEvents().forEach { event ->
      taskRepository.save(event = event)
    }

    return ChangeTaskOutputData.of(task = task)
  }
}
