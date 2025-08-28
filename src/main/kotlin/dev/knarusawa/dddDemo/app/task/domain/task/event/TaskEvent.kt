package dev.knarusawa.dddDemo.app.task.domain.task.event

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import dev.knarusawa.dddDemo.app.task.domain.TaskEventType
import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import org.springframework.context.ApplicationEvent
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type",
)
@JsonSubTypes(
  JsonSubTypes.Type(value = TaskCreated::class, name = "TASK_CREATED"),
  JsonSubTypes.Type(value = TaskChanged::class, name = "TASK_CHANGED"),
  JsonSubTypes.Type(value = TaskCompleted::class, name = "TASK_COMPLETED"),
)
sealed class TaskEvent(
  source: Any,
) : ApplicationEvent(source) {
  abstract val taskId: TaskId
  abstract val eventVersion: Long
  abstract val type: TaskEventType
  abstract val title: Title
  abstract val description: Description?
  abstract val assignee: ActorId?
  abstract val fromTime: FromTime?
  abstract val toTime: ToTime?
  abstract val occurredAt: LocalDateTime
}

data class TaskCreated
  @JsonCreator
  constructor(
    @JsonProperty("taskId") override val taskId: TaskId,
    @JsonProperty("eventVersion") override val eventVersion: Long,
    @JsonProperty("type") override val type: TaskEventType,
    @JsonProperty("title") override val title: Title,
    @JsonProperty("description") override val description: Description?,
    @JsonProperty("assignee") override val assignee: ActorId?,
    @JsonProperty("fromTime") override val fromTime: FromTime?,
    @JsonProperty("toTime") override val toTime: ToTime?,
    @JsonProperty("occurredAt") override val occurredAt: LocalDateTime = LocalDateTime.now(),
  ) : TaskEvent(
      source = taskId,
    ) {
    companion object {
      fun of(
        taskId: TaskId,
        eventVersion: Long,
        title: Title,
        description: Description?,
        assignee: ActorId?,
        fromTime: FromTime?,
        toTime: ToTime?,
      ) = TaskCreated(
        taskId = taskId,
        eventVersion = eventVersion,
        type = TaskEventType.TASK_CREATED,
        title = title,
        description = description,
        assignee = assignee,
        fromTime = fromTime,
        toTime = toTime,
        occurredAt = LocalDateTime.now(),
      )
    }
  }

data class TaskChanged
  @JsonCreator
  constructor(
    @JsonProperty("taskId") override val taskId: TaskId,
    @JsonProperty("eventVersion") override val eventVersion: Long,
    @JsonProperty("type") override val type: TaskEventType,
    @JsonProperty("title") override val title: Title,
    @JsonProperty("description") override val description: Description?,
    @JsonProperty("assignee") override val assignee: ActorId?,
    @JsonProperty("fromTime") override val fromTime: FromTime?,
    @JsonProperty("toTime") override val toTime: ToTime?,
    @JsonProperty("occurredAt") override val occurredAt: LocalDateTime = LocalDateTime.now(),
  ) : TaskEvent(
      source = taskId,
    ) {
    companion object {
      fun of(
        taskId: TaskId,
        eventVersion: Long,
        title: Title,
        description: Description?,
        assignee: ActorId?,
        fromTime: FromTime?,
        toTime: ToTime?,
      ) = TaskChanged(
        taskId = taskId,
        eventVersion = eventVersion,
        type = TaskEventType.TASK_CHANGED,
        title = title,
        description = description,
        assignee = assignee,
        fromTime = fromTime,
        toTime = toTime,
        occurredAt = LocalDateTime.now(),
      )
    }
  }

data class TaskCompleted
  @JsonCreator
  constructor(
    @JsonProperty("taskId") override val taskId: TaskId,
    @JsonProperty("eventVersion") override val eventVersion: Long,
    @JsonProperty("type") override val type: TaskEventType,
    @JsonProperty("title") override val title: Title,
    @JsonProperty("description") override val description: Description?,
    @JsonProperty("assignee") override val assignee: ActorId?,
    @JsonProperty("fromTime") override val fromTime: FromTime?,
    @JsonProperty("toTime") override val toTime: ToTime?,
    @JsonProperty("occurredAt") override val occurredAt: LocalDateTime = LocalDateTime.now(),
  ) : TaskEvent(
      source = taskId,
    ) {
    companion object {
      fun of(
        taskId: TaskId,
        eventVersion: Long,
        title: Title,
        description: Description?,
        assignee: ActorId?,
        fromTime: FromTime?,
        toTime: ToTime?,
      ) = TaskCompleted(
        taskId = taskId,
        eventVersion = eventVersion,
        type = TaskEventType.TASK_COMPLETED,
        title = title,
        description = description,
        assignee = assignee,
        fromTime = fromTime,
        toTime = toTime,
        occurredAt = LocalDateTime.now(),
      )
    }
  }
