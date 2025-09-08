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
    fun handle(cmd: CreateTaskCommand): Task {
      val state = TaskState.init(cmd)
      val created =
        TaskCreated.of(
          taskId = state.taskId,
          projectId = cmd.projectId,
          operator = cmd.operator,
          title = cmd.title,
          description = cmd.description,
          assigner = cmd.assigner,
          assignee = cmd.assignee,
          fromTime = cmd.fromTime,
          toTime = cmd.toTime,
        )
      return Task(
        state = state,
        events = mutableListOf(created),
      )
    }

    fun applyFromFirstEvent(events: List<TaskEvent>): Task {
      val created =
        events.firstOrNull() as? TaskCreated
          ?: throw IllegalStateException()

      val taskState = TaskState.init(event = created)
      val task = Task(state = taskState)
      events.forEachIndexed { index, event ->
        if (index == 0) {
          return@forEachIndexed
        }
        taskState.apply(event)
      }
      return task
    }
  }

  fun handle(cmd: ChangeTaskCommand) {
    if (cmd.taskId != this.state.taskId) {
      throw IllegalStateException()
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
    state.apply(changed)
    events.add(changed)
  }

  fun getEvents() = this.events.toList()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Task) return false

    if (state.taskId !== other.state.taskId) return false

    return true
  }

  override fun hashCode(): Int = state.taskId.hashCode()
}
