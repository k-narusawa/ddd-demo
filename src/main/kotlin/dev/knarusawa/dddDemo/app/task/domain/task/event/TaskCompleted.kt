package dev.knarusawa.dddDemo.app.task.domain.task.event

import dev.knarusawa.dddDemo.app.task.domain.TaskEventType
import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId
import java.time.LocalDateTime

data class TaskCompleted(
  override val taskId: TaskId,
  override val type: TaskEventType,
  override val teamId: TeamId,
  override val operator: ActorId,
  override val title: Title,
  override val description: Description?,
  override val assigner: ActorId?,
  override val assignee: ActorId?,
  override val fromTime: FromTime?,
  override val toTime: ToTime?,
  override val occurredAt: LocalDateTime = LocalDateTime.now(),
  override val completed: Boolean,
  override val version: Long,
) : TaskEvent(
    source = taskId,
  ) {
  companion object {
    fun of(
      taskId: TaskId,
      teamId: TeamId,
      operator: ActorId,
      title: Title,
      description: Description?,
      assigner: ActorId?,
      assignee: ActorId?,
      fromTime: FromTime?,
      toTime: ToTime?,
      version: Long,
    ) = TaskCompleted(
      taskId = taskId,
      teamId = teamId,
      operator = operator,
      type = TaskEventType.TASK_COMPLETED,
      title = title,
      description = description,
      assigner = assigner,
      assignee = assignee,
      fromTime = fromTime,
      toTime = toTime,
      occurredAt = LocalDateTime.now(),
      completed = true,
      version = version,
    )
  }
}
