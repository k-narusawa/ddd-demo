package dev.knarusawa.dddDemo.app.project.domain.project.event

import dev.knarusawa.dddDemo.app.project.domain.DomainEvent
import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectMember
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectName
import dev.knarusawa.dddDemo.publishedLanguage.project.proto.PLProjectEvent
import dev.knarusawa.dddDemo.util.ProtobufUtil
import java.time.LocalDateTime

sealed class ProjectEvent : DomainEvent<PLProjectEvent> {
  abstract override val eventId: ProjectEventId
  abstract val projectId: ProjectId
  abstract val type: ProjectEventType
  abstract override val occurredAt: LocalDateTime

  companion object {
    fun from(ba: ByteArray): ProjectEvent {
      val eventMessage = PLProjectEvent.parseFrom(ba)
      return fromPublishedLanguage(eventMessage)
    }

    fun fromPublishedLanguage(publishedLang: PLProjectEvent): ProjectEvent {
      val eventType = ProjectEventType.valueOf(publishedLang.type.name)
      return when (eventType) {
        ProjectEventType.PROJECT_CREATED ->
          ProjectCreated(
            eventId = ProjectEventId.from(value = publishedLang.eventId),
            projectId = ProjectId.from(value = publishedLang.projectId),
            type = ProjectEventType.PROJECT_CREATED,
            projectName =
              publishedLang.projectName?.let {
                ProjectName.of(value = it)
              } ?: throw IllegalStateException(),
            member =
              publishedLang.member?.let {
                ProjectMember.adminMember(memberId = MemberId.from(it.memberId))
              } ?: throw IllegalStateException(),
            occurredAt =
              ProtobufUtil.toLocalDateTime(publishedLang.occurredAt)
                ?: throw IllegalStateException(),
          )

        else -> throw IllegalStateException()
      }
    }
  }

  override fun toPublishedLanguage(): PLProjectEvent {
    val builder = PLProjectEvent.newBuilder()

    builder.setEventId(eventId.get())
    builder.setProjectId(projectId.get())
    builder.setType(type.toPublishedType())
    builder.setOccurredAt(ProtobufUtil.toTimestamp(occurredAt))

    return builder.build()
  }
}
