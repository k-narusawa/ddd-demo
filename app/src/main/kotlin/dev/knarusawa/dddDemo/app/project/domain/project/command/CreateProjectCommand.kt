package dev.knarusawa.dddDemo.app.project.domain.project.command

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectName

data class CreateProjectCommand(
  val projectName: ProjectName,
  val created: MemberId,
)
