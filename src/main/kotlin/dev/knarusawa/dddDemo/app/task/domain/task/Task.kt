package dev.knarusawa.dddDemo.app.task.domain.task

import dev.knarusawa.dddDemo.app.task.domain.task.command.CreateTaskCommand
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEvent

class Task private constructor(
  val state: TaskState,
  private val events: MutableList<TaskEvent> = mutableListOf(),
) {
  companion object {
    fun handle(cmd: CreateTaskCommand): Task {
      val event =
        TaskCreated.of(
          teamId = cmd.teamId,
          operator = cmd.operator,
          title = cmd.title,
          description = cmd.description,
          assigner = cmd.assigner,
          assignee = cmd.assignee,
          fromTime = cmd.fromTime,
          toTime = cmd.toTime,
        )
      return Task(
        state = TaskState.init(cmd),
        events = mutableListOf(event),
      )
    }

    fun applyFromFirstEvent(events: List<TaskEvent>): Task {
      val createdEvent =
        events.firstOrNull() as? TaskCreated
          ?: throw IllegalStateException()

      val taskState = TaskState.init(event = createdEvent)
      val task = Task(state = taskState)
      events.forEachIndexed { index, event ->
        if (index == 0) {
          return@forEachIndexed
        }
        taskState.apply(event)
        task.events.add(event)
      }
      return task
    }
  }

  fun getEvents() = this.events.toList()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Task) return false

    if (state?.taskId !== other.state?.taskId) return false

    return true
  }

  override fun hashCode(): Int = state?.taskId.hashCode()
}
