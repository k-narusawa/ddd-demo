package dev.knarusawa.dddDemo.app.project.domain.task

import dev.knarusawa.dddDemo.app.project.domain.task.command.ChangeTaskCommand
import dev.knarusawa.dddDemo.app.project.domain.task.command.CreateTaskCommand
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEvent

class Task private constructor(
  val state: TaskState,
  private val taskEvents: MutableList<TaskEvent> = mutableListOf(),
) {
  companion object {
    fun handle(cmd: CreateTaskCommand): Task {
      val state = TaskState.init(cmd)
      val taskCreated =
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
        taskEvents = mutableListOf(taskCreated),
      )
    }

    fun applyFromFirstEvent(taskEvents: List<TaskEvent>): Task {
      val taskCreated =
        taskEvents.firstOrNull() as? TaskCreated
          ?: throw IllegalStateException()

      val taskState = TaskState.init(event = taskCreated)
      val task = Task(state = taskState)
      taskEvents.forEachIndexed { index, event ->
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

    val taskChanged =
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
    state.apply(taskChanged)
    taskEvents.add(taskChanged)
  }

  fun getEvents() = this.taskEvents.toList()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Task) return false

    if (state.taskId !== other.state.taskId) return false

    return true
  }

  override fun hashCode(): Int = state.taskId.hashCode()
}
