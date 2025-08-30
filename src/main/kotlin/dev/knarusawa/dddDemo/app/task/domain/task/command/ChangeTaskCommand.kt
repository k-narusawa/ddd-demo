package dev.knarusawa.dddDemo.app.task.domain.task.command

import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime

data class ChangeTaskCommand(
  val taskId: TaskId,
  val operator: MemberId,
  val title: Title,
  val description: Description?,
  val assigner: MemberId?,
  val assignee: MemberId?,
  val fromTime: FromTime?,
  val toTime: ToTime?,
)
