package dev.knarusawa.dddDemo.app.project.domain.task.event

import dev.knarusawa.dddDemo.app.project.domain.DomainEvent
import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.task.Description
import dev.knarusawa.dddDemo.app.project.domain.task.FromTime
import dev.knarusawa.dddDemo.app.project.domain.task.TaskId
import dev.knarusawa.dddDemo.app.project.domain.task.Title
import dev.knarusawa.dddDemo.app.project.domain.task.ToTime
import dev.knarusawa.dddDemo.publishedLanguage.project.proto.TaskEventMessage
import dev.knarusawa.dddDemo.util.JsonUtil
import dev.knarusawa.dddDemo.util.ProtobufUtil
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
sealed class TaskEvent : DomainEvent {
  abstract override val eventId: TaskEventId
  abstract val taskId: TaskId
  abstract val type: TaskEventType
  abstract val projectId: ProjectId
  abstract val operator: MemberId
  abstract val title: Title
  abstract val description: Description?
  abstract val assigner: MemberId?
  abstract val assignee: MemberId?
  abstract val fromTime: FromTime?
  abstract val toTime: ToTime?
  abstract override val occurredAt: LocalDateTime
  abstract val completed: Boolean

  companion object {
    fun fromPayload(payload: String): TaskEvent = JsonUtil.json.decodeFromString<TaskEvent>(payload)

    fun fromEventMessage(ba: ByteArray): TaskEvent {
      val eventMessage = TaskEventMessage.parseFrom(ba)
      val eventType = TaskEventType.valueOf(eventMessage.type.name)
      return when (eventType) {
        TaskEventType.TASK_CREATED ->
          TaskCreated(
            eventId = TaskEventId.from(value = eventMessage.eventId),
            taskId = TaskId.from(value = eventMessage.taskId),
            type = TaskEventType.TASK_CREATED,
            projectId = ProjectId.from(value = eventMessage.projectId),
            operator = MemberId.from(value = eventMessage.operatorId),
            title = Title.of(value = eventMessage.title),
            description =
              if (eventMessage.hasDescription()) {
                Description.of(value = eventMessage.description)
              } else {
                null
              },
            assigner =
              if (eventMessage.hasAssignerId()) {
                MemberId.from(value = eventMessage.assignerId)
              } else {
                null
              },
            assignee =
              if (eventMessage.hasAssigneeId()) {
                MemberId.from(value = eventMessage.assigneeId)
              } else {
                null
              },
            fromTime =
              ProtobufUtil
                .toLocalDateTime(eventMessage.fromTime)
                ?.let { FromTime.of(value = it) },
            toTime =
              ProtobufUtil
                .toLocalDateTime(eventMessage.toTime)
                ?.let { ToTime.of(value = it) },
            occurredAt =
              ProtobufUtil.toLocalDateTime(eventMessage.occurredAt)
                ?: throw IllegalStateException(),
            completed = eventMessage.completed,
          )

        TaskEventType.TASK_CHANGED ->
          TaskChanged(
            eventId = TaskEventId.from(value = eventMessage.eventId),
            taskId = TaskId.from(value = eventMessage.taskId),
            type = TaskEventType.TASK_CHANGED,
            projectId = ProjectId.from(value = eventMessage.projectId),
            operator = MemberId.from(value = eventMessage.operatorId),
            title = Title.of(value = eventMessage.title),
            description = Description.of(value = eventMessage.description),
            assigner = MemberId.from(value = eventMessage.assignerId),
            assignee = MemberId.from(value = eventMessage.assigneeId),
            fromTime =
              ProtobufUtil
                .toLocalDateTime(eventMessage.fromTime)
                ?.let { FromTime.of(value = it) },
            toTime =
              ProtobufUtil
                .toLocalDateTime(eventMessage.toTime)
                ?.let { ToTime.of(value = it) },
            occurredAt =
              ProtobufUtil.toLocalDateTime(eventMessage.occurredAt)
                ?: throw IllegalStateException(),
            completed = eventMessage.completed,
          )

        TaskEventType.TASK_COMPLETED ->
          TaskCompleted(
            eventId = TaskEventId.from(value = eventMessage.eventId),
            taskId = TaskId.from(value = eventMessage.taskId),
            type = TaskEventType.TASK_COMPLETED,
            projectId = ProjectId.from(value = eventMessage.projectId),
            operator = MemberId.from(value = eventMessage.operatorId),
            title = Title.of(value = eventMessage.title),
            description = Description.of(value = eventMessage.description),
            assigner = MemberId.from(value = eventMessage.assignerId),
            assignee = MemberId.from(value = eventMessage.assigneeId),
            fromTime =
              ProtobufUtil
                .toLocalDateTime(eventMessage.fromTime)
                ?.let { FromTime.of(value = it) },
            toTime =
              ProtobufUtil
                .toLocalDateTime(eventMessage.toTime)
                ?.let { ToTime.of(value = it) },
            occurredAt =
              ProtobufUtil.toLocalDateTime(eventMessage.occurredAt)
                ?: throw IllegalStateException(),
            completed = eventMessage.completed,
          )
      }
    }
  }

  fun toPayload(): String = JsonUtil.json.encodeToString(serializer(), this)

  fun toEventMessage(): TaskEventMessage {
    val builder =
      TaskEventMessage
        .newBuilder()
        .setEventId(eventId.get())
        .setTaskId(taskId.get())
        .setType(type.toPublishedType())
        .setProjectId(projectId.get())
        .setOperatorId(operator.get())
        .setTitle(title.get())
        .setOccurredAt(ProtobufUtil.toTimestamp(occurredAt))
        .setCompleted(completed)

    description?.let { builder.setDescription(it.get()) }
    assigner?.let { builder.setAssignerId(it.get()) }
    assignee?.let { builder.setAssigneeId(it.get()) }
    fromTime?.let { builder.setFromTime(ProtobufUtil.toTimestamp(it.get())) }
    toTime?.let { builder.setToTime(ProtobufUtil.toTimestamp(it.get())) }

    return builder.build()
  }
}
