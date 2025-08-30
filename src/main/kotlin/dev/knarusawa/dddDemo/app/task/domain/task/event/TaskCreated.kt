package dev.knarusawa.dddDemo.app.task.domain.task.event

import dev.knarusawa.dddDemo.app.task.adapter.gateway.kurrentdb.eventData.TaskEventData
import dev.knarusawa.dddDemo.app.task.domain.TaskEventType
import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId
import java.time.LocalDateTime

data class TaskCreated private constructor(
  override val taskId: TaskId,
  override val type: TaskEventType,
  override val teamId: TeamId,
  override val operator: MemberId,
  override val title: Title,
  override val description: Description?,
  override val assigner: MemberId?,
  override val assignee: MemberId?,
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
      teamId: TeamId,
      operator: MemberId,
      title: Title,
      description: Description?,
      assigner: MemberId?,
      assignee: MemberId?,
      fromTime: FromTime?,
      toTime: ToTime?,
    ) = TaskCreated(
      taskId = TaskId.new(),
      teamId = teamId,
      operator = operator,
      type = TaskEventType.TASK_CREATED,
      title = title,
      description = description,
      assigner = assigner,
      assignee = assignee,
      fromTime = fromTime,
      toTime = toTime,
      occurredAt = LocalDateTime.now(),
      completed = false,
      version = 1,
    )

    fun of(eventData: TaskEventData) =
      TaskCreated(
        taskId = TaskId.from(value = eventData.taskId),
        type = TaskEventType.TASK_CREATED,
        teamId = TeamId.from(value = eventData.taskId),
        operator = MemberId.from(value = eventData.operator),
        title = Title.of(value = eventData.title),
        description = eventData.description?.let { Description.of(value = it) },
        assigner = eventData.assigner?.let { MemberId.from(value = it) },
        assignee = eventData.assignee?.let { MemberId.from(value = it) },
        fromTime = eventData.fromTime?.let { FromTime.of(value = it) },
        toTime = eventData.toTime?.let { ToTime.of(value = it) },
        occurredAt = eventData.occurredAt,
        completed = eventData.completed,
        version = eventData.version,
      )
  }
}
