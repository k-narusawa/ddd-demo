package dev.knarusawa.dddDemo.app.project.domain.project.event

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectMember
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectName
import dev.knarusawa.dddDemo.publishedLanguage.project.proto.PLProjectEvent
import java.time.LocalDateTime

data class ProjectCreated(
  override val eventId: ProjectEventId,
  override val projectId: ProjectId,
  override val type: ProjectEventType = ProjectEventType.PROJECT_CREATED,
  val projectName: ProjectName,
  val member: ProjectMember,
  override val occurredAt: LocalDateTime = LocalDateTime.now(),
) : ProjectEvent() {
  companion object {
    fun of(
      projectName: ProjectName,
      created: MemberId,
    ) = ProjectCreated(
      eventId = ProjectEventId.new(),
      projectId = ProjectId.new(),
      projectName = projectName,
      member = ProjectMember.adminMember(memberId = created),
    )
  }

  override fun toPublishedLanguage(): PLProjectEvent {
    val builder = super.toPublishedLanguage().toBuilder()
    builder.setProjectName(projectName.get())
    builder.setMember(member.toPublishedLanguage())
    return builder.build()
  }
}
