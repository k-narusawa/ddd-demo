package dev.knarusawa.dddDemo.app.project.domain.task

import dev.knarusawa.dddDemo.app.project.domain.task.command.ChangeTaskCommand
import dev.knarusawa.dddDemo.app.project.domain.task.command.CreateTaskCommand
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEvent

class Task private constructor(
  val state: TaskState,
  private val events: MutableList<TaskEvent> = mutableListOf(),
) {
  companion object {
    fun handle(cmd: CreateTaskCommand): List<TaskEvent> {
      val created =
        TaskCreated.of(
          taskId = TaskId.new(),
          projectId = cmd.projectId,
          operator = cmd.operator,
          title = cmd.title,
          description = cmd.description,
          assigner = cmd.assigner,
          assignee = cmd.assignee,
          fromTime = cmd.fromTime,
          toTime = cmd.toTime,
        )
      return listOf(created)
    }

    fun from(pastEvents: List<TaskEvent>): Task {
      val taskCreated =
        pastEvents.firstOrNull() as? TaskCreated
          ?: throw IllegalStateException()

      val taskState = TaskState.init(event = taskCreated)
      val task = Task(state = taskState)
      pastEvents.forEachIndexed { index, event ->
        if (index == 0) {
          return@forEachIndexed
        }
        taskState.apply(event)
      }
      return task
    }
  }

  fun getEvents() = this.events.toList()

  fun handle(cmd: ChangeTaskCommand): List<TaskEvent> {
    if (cmd.taskId != this.state.taskId) {
      throw IllegalStateException()
    }

    if (this.state.completed) {
      throw IllegalStateException("完了状態のタスクは変更不可")
    }

    val changed =
      TaskChanged.of(
        taskId = cmd.taskId,
        projectId = this.state.projectId,
        operator = cmd.operator,
        title = cmd.title,
        description = cmd.description,
        assigner = cmd.assigner,
        assignee = cmd.assignee,
        fromTime = cmd.fromTime,
        toTime = cmd.toTime,
      )
    return listOf(changed)
  }

  fun apply(event: TaskEvent) {
    this.state.apply(event = event)
    this.events.add(event)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Task) return false

    if (state.taskId != other.state.taskId) return false

    return true
  }

  override fun hashCode(): Int = state.taskId.hashCode()
}
