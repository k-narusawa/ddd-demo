package dev.knarusawa.dddDemo.app.task.domain.task

import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.task.domain.task.command.CreateTaskCommand
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCompleted
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEvent

class TaskState private constructor(
  val taskId: TaskId,
  val projectId: ProjectId,
  operator: MemberId,
  title: Title,
  description: Description?,
  assigner: MemberId?,
  assignee: MemberId?,
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
        projectId = cmd.projectId,
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
        projectId = event.projectId,
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
    if (this.version + 1 >= event.version) {
      throw IllegalStateException()
    }
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
