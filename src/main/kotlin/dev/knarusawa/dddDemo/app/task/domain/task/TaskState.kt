package dev.knarusawa.dddDemo.app.task.domain.task

import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
import dev.knarusawa.dddDemo.app.task.domain.task.command.CreateTaskCommand
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCompleted
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEvent
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId

class TaskState private constructor(
  val taskId: TaskId,
  val teamId: TeamId,
  var operator: ActorId,
  var title: Title,
  var description: Description?,
  var assigner: ActorId?,
  var assignee: ActorId?,
  var fromTime: FromTime?,
  var toTime: ToTime?,
  var completed: Boolean,
  var version: Long,
) {
  companion object {
    fun init(cmd: CreateTaskCommand) =
      TaskState(
        teamId = cmd.teamId,
        operator = cmd.operator,
        taskId = TaskId.new(),
        title = cmd.title,
        description = cmd.description,
        assigner = cmd.assigner,
        assignee = cmd.assignee,
        fromTime = cmd.fromTime,
        toTime = cmd.toTime,
        completed = false,
        version = 1,
      )
  }

  fun apply(event: TaskEvent) {
    when (event) {
      TaskChanged -> {
        this.operator = event.operator
        this.title = event.title
        this.description = event.description
        this.assigner = event.assigner
        this.assignee = event.assignee
        this.fromTime = event.fromTime
        this.toTime = event.toTime
        this.version = event.version
      }

      TaskCompleted -> {
        this.operator = event.operator
        this.title = event.title
        this.description = event.description
        this.assigner = event.assigner
        this.assignee = event.assignee
        this.fromTime = event.fromTime
        this.toTime = event.toTime
        this.completed = true
        this.version = event.version
      }

      TaskCreated -> throw IllegalStateException()
      else -> throw IllegalStateException()
    }
  }
}
