package dev.knarusawa.dddDemo.app.project.domain.task

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.task.command.CreateTaskCommand
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskCompleted
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEvent

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
        taskId = TaskId.new(),
        projectId = cmd.projectId,
        operator = cmd.operator,
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

  fun apply(taskEvent: TaskEvent) {
    when (taskEvent) {
      is TaskChanged -> {
        this.operator = taskEvent.operator
        this.title = taskEvent.title
        this.description = taskEvent.description
        this.assigner = taskEvent.assigner
        this.assignee = taskEvent.assignee
        this.fromTime = taskEvent.fromTime
        this.toTime = taskEvent.toTime
        this.version += this.version + 1
      }

      is TaskCompleted -> {
        this.operator = taskEvent.operator
        this.title = taskEvent.title
        this.description = taskEvent.description
        this.assigner = taskEvent.assigner
        this.assignee = taskEvent.assignee
        this.fromTime = taskEvent.fromTime
        this.toTime = taskEvent.toTime
        this.completed = true
        this.version += this.version + 1
      }

      is TaskCreated -> throw IllegalStateException("作成イベントの適用は不可能")
    }
  }
}
