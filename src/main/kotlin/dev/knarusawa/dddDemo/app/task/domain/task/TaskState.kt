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
  operator: ActorId,
  title: Title,
  description: Description?,
  assigner: ActorId?,
  assignee: ActorId?,
  fromTime: FromTime?,
  toTime: ToTime?,
  completed: Boolean,
  version: Long,
) {
  var operator = operator
    private set
  var title = title
    private set
  var description = description
    private set
  var assigner = assigner
    private set
  var assignee = assignee
    private set
  var fromTime = fromTime
    private set
  var toTime = toTime
    private set
  var completed = completed
    private set
  var version = version
    private set

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

    fun init(event: TaskCreated) =
      TaskState(
        teamId = event.teamId,
        operator = event.operator,
        taskId = event.taskId,
        title = event.title,
        description = event.description,
        assigner = event.assigner,
        assignee = event.assignee,
        fromTime = event.fromTime,
        toTime = event.toTime,
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
