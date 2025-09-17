package dev.knarusawa.dddDemo.app.project.domain.project

import dev.knarusawa.dddDemo.app.project.domain.project.command.CreateProjectCommand
import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectCreated

class ProjectState private constructor(
  val projectId: ProjectId,
  projectName: ProjectName,
  members: MutableList<ProjectMember> = mutableListOf(),
) {
  var projectName = projectName
    private set
  var members = members
    private set

  companion object {
    fun of(cmd: CreateProjectCommand) =
      ProjectState(
        projectId = ProjectId.new(),
        projectName = cmd.projectName,
        members =
          mutableListOf(
            ProjectMember.adminMember(memberId = cmd.created),
          ),
      )

    fun init(event: ProjectCreated) =
      ProjectState(
        projectId = event.projectId,
        projectName = event.projectName,
        members =
          mutableListOf(
            ProjectMember.adminMember(memberId = event.member.memberId),
          ),
      )
  }
}
