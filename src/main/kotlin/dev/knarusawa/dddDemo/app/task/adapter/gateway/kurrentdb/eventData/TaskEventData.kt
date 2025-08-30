package dev.knarusawa.dddDemo.app.task.adapter.gateway.kurrentdb.eventData

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import dev.knarusawa.dddDemo.app.task.domain.TaskEventType
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEvent
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskEventData
  @JsonCreator
  constructor(
    @JsonProperty("taskId") val taskId: String,
    @JsonProperty("type") val type: TaskEventType,
    @JsonProperty("projectId") val projectId: String,
    @JsonProperty("operator") val operator: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String?,
    @JsonProperty("assigner") val assigner: String?,
    @JsonProperty("assignee") val assignee: String?,
    @JsonProperty("fromTime") val fromTime: LocalDateTime?,
    @JsonProperty("toTime") val toTime: LocalDateTime?,
    @JsonProperty("occurredAt") val occurredAt: LocalDateTime,
    @JsonProperty("completed") val completed: Boolean,
    @JsonProperty("version") val version: Long,
  ) {
    companion object {
      fun from(event: TaskEvent) =
        TaskEventData(
          taskId = event.taskId.get(),
          type = event.type,
          projectId = event.projectId.get(),
          operator = event.operator.get(),
          title = event.title.get(),
          description = event.description?.get(),
          assigner = event.assigner?.get(),
          assignee = event.assignee?.get(),
          fromTime = event.fromTime?.get(),
          toTime = event.toTime?.get(),
          occurredAt = event.occurredAt,
          completed = event.completed,
          version = event.version,
        )
    }

    fun to(): TaskEvent =
      when (this.type) {
        TaskEventType.TASK_CREATED -> TaskCreated.of(this)
        TaskEventType.TASK_CHANGED -> TaskChanged.of(this)
        else -> {
          throw RuntimeException()
        }
      }
  }
