package dev.knarusawa.dddDemo.app.task.application.usecase.createTask

import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId
import java.time.LocalDateTime

data class CreateTaskInputData private constructor(
  val teamId: TeamId,
  val operator: MemberId,
  val title: Title,
  val description: Description?,
  val assigner: MemberId?,
  val assignee: MemberId?,
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
      operator = MemberId.from(value = operator),
      title = Title.of(value = title),
      description = description?.let { Description.of(value = it) },
      assigner = assigner?.let { MemberId.from(value = it) },
      assignee = assignee?.let { MemberId.from(value = it) },
      fromTime = fromTime?.let { FromTime.of(value = it) },
      toTime = toTime?.let { ToTime.of(value = it) },
    )
  }
}
