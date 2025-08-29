package dev.knarusawa.dddDemo.app.task.domain.task.event

import dev.knarusawa.dddDemo.app.task.domain.TaskEventType
import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId
import org.springframework.context.ApplicationEvent
import java.time.LocalDateTime

sealed class TaskEvent(
  source: Any,
) : ApplicationEvent(source) {
  abstract val taskId: TaskId
  abstract val type: TaskEventType
  abstract val teamId: TeamId
  abstract val operator: ActorId
  abstract val title: Title
  abstract val description: Description?
  abstract val assigner: ActorId?
  abstract val assignee: ActorId?
  abstract val fromTime: FromTime?
  abstract val toTime: ToTime?
  abstract val occurredAt: LocalDateTime
  abstract val completed: Boolean
  abstract val version: Long
}
