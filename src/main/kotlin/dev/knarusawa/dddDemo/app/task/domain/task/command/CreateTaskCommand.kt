package dev.knarusawa.dddDemo.app.task.domain.task.command

import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId

data class CreateTaskCommand(
  val teamId: TeamId,
  val operator: ActorId,
  val title: Title,
  val description: Description?,
  val assigner: ActorId?,
  val assignee: ActorId?,
  val fromTime: FromTime?,
  val toTime: ToTime?,
)
