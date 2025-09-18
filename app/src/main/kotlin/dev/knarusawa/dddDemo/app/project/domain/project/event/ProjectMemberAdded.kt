package dev.knarusawa.dddDemo.app.project.domain.project.event

import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectMember
import dev.knarusawa.dddDemo.publishedLanguage.project.proto.PLProjectEvent
import java.time.LocalDateTime

data class ProjectMemberAdded(
  override val eventId: ProjectEventId,
  override val projectId: ProjectId,
  override val type: ProjectEventType = ProjectEventType.PROJECT_MEMBER_ADDED,
  val addedMembers: List<ProjectMember>,
  override val occurredAt: LocalDateTime = LocalDateTime.now(),
) : ProjectEvent() {
  companion object {
    fun of(
      projectId: ProjectId,
      addedMembers: List<ProjectMember>,
    ) = ProjectMemberAdded(
      eventId = ProjectEventId.new(),
      projectId = projectId,
      addedMembers = addedMembers,
    )
  }

  override fun toPublishedLanguage(): PLProjectEvent {
    val builder = super.toPublishedLanguage().toBuilder()
    addedMembers.forEach {
      builder.addAddedMembers(it.toPublishedLanguage())
    }
    return builder.build()
  }
}
