package dev.knarusawa.dddDemo.app.task.application.usecase

import dev.knarusawa.dddDemo.app.task.application.port.TaskInputBoundary
import dev.knarusawa.dddDemo.app.task.application.usecase.createTask.CreateTaskInputData
import dev.knarusawa.dddDemo.app.task.application.usecase.createTask.CreateTaskOutputData
import dev.knarusawa.dddDemo.app.task.domain.task.Task
import dev.knarusawa.dddDemo.app.task.domain.task.command.CreateTaskCommand
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEventStoreRepository
import dev.knarusawa.dddDemo.app.task.domain.team.TeamRepository
import org.springframework.stereotype.Service

@Service
class TaskInteractor(
  private val teamRepository: TeamRepository,
  private val taskEventStoreRepository: TaskEventStoreRepository,
) : TaskInputBoundary {
  override fun handle(input: CreateTaskInputData): CreateTaskOutputData {
    val team = teamRepository.findByTeamId(teamId = input.teamId) ?: throw RuntimeException()

    if (!team.hasWriteRole(input.operator)) {
      throw RuntimeException() // TODO: 専用の例外クラスを作成する
    }

    val cmd =
      CreateTaskCommand(
        teamId = input.teamId,
        operator = input.operator,
        title = input.title,
        description = input.description,
        assigner = input.assigner,
        assignee = input.assignee,
        fromTime = input.fromTime,
        toTime = input.toTime,
      )
    val task = Task.handle(cmd = cmd)
    task.getEvents().forEach {
      taskEventStoreRepository.commit(event = it)
    }

    return CreateTaskOutputData.of(task = task)
  }
}
