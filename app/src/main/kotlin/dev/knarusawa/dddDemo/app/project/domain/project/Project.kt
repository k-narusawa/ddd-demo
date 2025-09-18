package dev.knarusawa.dddDemo.app.project.domain.project

import dev.knarusawa.dddDemo.app.project.domain.exception.ProjectNotFound
import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.command.AddProjectMemberCommand
import dev.knarusawa.dddDemo.app.project.domain.project.command.CreateProjectCommand
import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectCreated
import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectEvent
import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectMemberAdded
import dev.knarusawa.dddDemo.util.logger

class Project private constructor(
  val projectId: ProjectId,
  projectName: ProjectName,
  members: MutableSet<ProjectMember>,
  private val events: MutableList<ProjectEvent> = mutableListOf(),
) {
  var projectName = projectName
    private set
  var members = members
    private set

  fun getEvents() = this.events.toList()

  companion object {
    private val log = logger()

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
        members =
          mutableSetOf(
            ProjectMember.adminMember(memberId = event.createMember.memberId),
          ),
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

  fun handle(cmd: AddProjectMemberCommand): List<ProjectEvent> {
    if (cmd.projectId != this.projectId) {
      throw IllegalStateException("ProjectIdの不一致")
    }

    cmd.addedMembers.forEach { addedMember ->
      if (this.members.contains(addedMember)) {
        log.warn("すでに登録済みのユーザ memberId: ${addedMember.memberId}")
      }
    }

    val added =
      ProjectMemberAdded.of(
        projectId = cmd.projectId,
        addedMembers = cmd.addedMembers,
      )
    return listOf(added)
  }

  fun apply(event: ProjectMemberAdded) {
    event.addedMembers.forEach { addedMember ->
      if (this.members.contains(addedMember)) {
        // すでに存在するメンバーはRole変更の対応のために一度削除してから追加
        this.members.remove(addedMember)
      }
      this.members.add(addedMember)
    }
  }

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
