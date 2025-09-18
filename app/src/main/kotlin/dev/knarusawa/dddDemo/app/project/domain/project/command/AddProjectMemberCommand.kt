package dev.knarusawa.dddDemo.app.project.domain.project.command

import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectMember

data class AddProjectMemberCommand(
  val projectId: ProjectId,
  val addedMembers: Set<ProjectMember>,
)
