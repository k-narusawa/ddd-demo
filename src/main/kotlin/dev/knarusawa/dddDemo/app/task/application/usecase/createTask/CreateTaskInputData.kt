package dev.knarusawa.dddDemo.app.task.application.usecase.createTask

import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId
import java.time.LocalDateTime

data class CreateTaskInputData private constructor(
  val teamId: TeamId,
  val operator: ActorId,
  val title: Title,
  val description: Description?,
  val assigner: ActorId?,
  val assignee: ActorId?,
  val fromTime: FromTime?,
  val toTime: ToTime?,
) {
  companion object {
    fun of(
      teamId: String,
      operator: String,
      title: String,
      description: String?,
      assigner: String?,
      assignee: String?,
      fromTime: LocalDateTime?,
      toTime: LocalDateTime?,
    ) = CreateTaskInputData(
      teamId = TeamId.from(value = teamId),
      operator = ActorId.from(value = operator),
      title = Title.of(value = title),
      description = description?.let { Description.of(value = it) },
      assigner = assigner?.let { ActorId.from(value = it) },
      assignee = assignee?.let { ActorId.from(value = it) },
      fromTime = fromTime?.let { FromTime.of(value = it) },
      toTime = toTime?.let { ToTime.of(value = it) },
    )
  }
}
