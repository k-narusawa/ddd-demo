package dev.knarusawa.dddDemo.app.project.domain.project

import dev.knarusawa.dddDemo.app.project.domain.exception.ProjectNotFound
import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.command.CreateProjectCommand
import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectCreated
import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectEvent

class Project private constructor(
  val state: ProjectState,
  private val events: MutableList<ProjectEvent> = mutableListOf(),
) {
  companion object {
    fun create(cmd: CreateProjectCommand): Project {
      val created =
        ProjectCreated.of(
          projectName = cmd.projectName,
          created = cmd.created,
        )
      val project = Project(state = ProjectState.init(event = created))
      project.events.add(created)
      return project
    }

    fun from(pastEvents: List<ProjectEvent>): Project {
      if (pastEvents.isEmpty()) {
        throw ProjectNotFound(message = "イベントが存在しない")
      }

      val created =
        pastEvents.firstOrNull()
          as? ProjectCreated
          ?: throw IllegalStateException("初期イベントが作成イベントでない event: ${pastEvents.firstOrNull()}")
      val state = ProjectState.init(created)

      pastEvents.sortedBy(ProjectEvent::occurredAt).forEachIndexed { index, event ->
        if (index == 0) {
          return@forEachIndexed
        }
      }

      return Project(state = state)
    }
  }

  fun getEvents() = this.events.toList()

  fun hasWriteRole(memberId: MemberId): Boolean {
    return this.state.members.any { member ->
      return if (member.memberId == memberId) {
        member.role == MemberRole.ADMIN || member.role == MemberRole.WRITE
      } else {
        false
      }
    }
  }
}
