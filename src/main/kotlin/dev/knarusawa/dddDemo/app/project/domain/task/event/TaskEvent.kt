package dev.knarusawa.dddDemo.app.project.domain.task.event

import dev.knarusawa.dddDemo.app.project.TaskDomainEvent
import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.task.Description
import dev.knarusawa.dddDemo.app.project.domain.task.FromTime
import dev.knarusawa.dddDemo.app.project.domain.task.TaskId
import dev.knarusawa.dddDemo.app.project.domain.task.Title
import dev.knarusawa.dddDemo.app.project.domain.task.ToTime
import dev.knarusawa.dddDemo.util.JsonUtil
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
sealed class TaskEvent : TaskDomainEvent() {
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
  abstract val occurredAt: LocalDateTime
  abstract val completed: Boolean

  companion object {
    fun fromPayload(payload: String): TaskEvent = JsonUtil.json.decodeFromString<TaskEvent>(payload)
  }

  fun toPayload(): String = JsonUtil.json.encodeToString(TaskEvent.serializer(), this)

//  fun toPayload(): String =
//    when (this) {
//      is TaskCreated -> JsonUtil.json.encodeToString(TaskCreated.serializer(), this)
//      is TaskChanged -> JsonUtil.json.encodeToString(TaskChanged.serializer(), this)
//      is TaskCompleted -> JsonUtil.json.encodeToString(TaskCompleted.serializer(), this)
//    }
}
