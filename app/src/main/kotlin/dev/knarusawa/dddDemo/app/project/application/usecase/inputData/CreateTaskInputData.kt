package dev.knarusawa.dddDemo.app.project.application.usecase.inputData

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.task.Description
import dev.knarusawa.dddDemo.app.project.domain.task.FromTime
import dev.knarusawa.dddDemo.app.project.domain.task.Title
import dev.knarusawa.dddDemo.app.project.domain.task.ToTime
import java.time.LocalDateTime

data class CreateTaskInputData private constructor(
  val projectId: ProjectId,
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
      projectId: String,
      operator: String,
      title: String,
      description: String?,
      assigner: String?,
      assignee: String?,
      fromTime: LocalDateTime?,
      toTime: LocalDateTime?,
    ) = CreateTaskInputData(
      projectId = ProjectId.from(value = projectId),
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
