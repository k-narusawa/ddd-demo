package dev.knarusawa.dddDemo.app.project.domain.task

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
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
    when (event) {
      is TaskChanged -> {
        this.operator = event.operator
        this.title = event.title
        this.description = event.description
        this.assigner = event.assigner
        this.assignee = event.assignee
        this.fromTime = event.fromTime
        this.toTime = event.toTime
        this.version += this.version + 1
      }

      is TaskCompleted -> {
        this.operator = event.operator
        this.title = event.title
        this.description = event.description
        this.assigner = event.assigner
        this.assignee = event.assignee
        this.fromTime = event.fromTime
        this.toTime = event.toTime
        this.completed = true
        this.version += this.version + 1
      }

      is TaskCreated -> throw IllegalStateException("作成イベントの適用は不可能")
    }
  }
}
