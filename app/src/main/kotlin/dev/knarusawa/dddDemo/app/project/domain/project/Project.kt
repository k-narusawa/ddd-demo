package dev.knarusawa.dddDemo.app.project.domain.project

import dev.knarusawa.dddDemo.app.project.domain.exception.ProjectNotFound
import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.command.CreateProjectCommand
import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectCreated
import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectEvent

class Project private constructor(
  val projectId: ProjectId,
  projectName: ProjectName,
  members: MutableList<ProjectMember>,
  private val events: MutableList<ProjectEvent> = mutableListOf(),
) {
  var projectName = projectName
    private set
  var members = members
    private set

  companion object {
    fun create(cmd: CreateProjectCommand): Project {
      val created =
        ProjectCreated.of(
          projectName = cmd.projectName,
          created = cmd.created,
        )
      val project = of(event = created)
      project.events.add(created)
      return project
    }

    private fun of(event: ProjectCreated) =
      Project(
        projectId = event.projectId,
        projectName = event.projectName,
        members = mutableListOf(ProjectMember.adminMember(memberId = event.member.memberId)),
      )

    fun from(pastEvents: List<ProjectEvent>): Project {
      if (pastEvents.isEmpty()) {
        throw ProjectNotFound(message = "イベントが存在しない")
      }

      val created =
        pastEvents.firstOrNull()
          as? ProjectCreated
          ?: throw IllegalStateException("初期イベントが作成イベントでない event: ${pastEvents.firstOrNull()}")
      val project = of(created)

      pastEvents.sortedBy(ProjectEvent::occurredAt).forEachIndexed { index, event ->
        if (index == 0) {
          return@forEachIndexed
        }
      }

      return project
    }
  }

  fun getEvents() = this.events.toList()

  fun hasWriteRole(memberId: MemberId): Boolean {
    return this.members.any { member ->
      return if (member.memberId == memberId) {
        member.role == MemberRole.ADMIN || member.role == MemberRole.WRITE
      } else {
        false
      }
    }
  }
}
