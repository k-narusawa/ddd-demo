package dev.knarusawa.dddDemo.app.project.domain.task.event

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.task.Description
import dev.knarusawa.dddDemo.app.project.domain.task.FromTime
import dev.knarusawa.dddDemo.app.project.domain.task.TaskId
import dev.knarusawa.dddDemo.app.project.domain.task.Title
import dev.knarusawa.dddDemo.app.project.domain.task.ToTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TaskCompleted(
  override val eventId: TaskEventId,
  override val taskId: TaskId,
  override val type: TaskEventType,
  override val projectId: ProjectId,
  override val operator: MemberId,
  override val title: Title,
  override val description: Description?,
  override val assigner: MemberId?,
  override val assignee: MemberId?,
  override val fromTime: FromTime?,
  override val toTime: ToTime?,
  @Contextual
  override val occurredAt: LocalDateTime = LocalDateTime.now(),
  override val completed: Boolean,
) : TaskEvent() {
  companion object {
    fun of(
      taskId: TaskId,
      projectId: ProjectId,
      operator: MemberId,
      title: Title,
      description: Description?,
      assigner: MemberId?,
      assignee: MemberId?,
      fromTime: FromTime?,
      toTime: ToTime?,
    ) = TaskCompleted(
      eventId = TaskEventId.new(),
      taskId = taskId,
      projectId = projectId,
      operator = operator,
      type = TaskEventType.TASK_COMPLETED,
      title = title,
      description = description,
      assigner = assigner,
      assignee = assignee,
      fromTime = fromTime,
      toTime = toTime,
      occurredAt = LocalDateTime.now(),
      completed = false,
    )
  }
}
