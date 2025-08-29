package dev.knarusawa.dddDemo.app.task.adapter.gateway.kurrentdb.eventData

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import dev.knarusawa.dddDemo.app.task.domain.TaskEventType
import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEvent
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskEventData
  @JsonCreator
  constructor(
    @JsonProperty("taskId") val taskId: String,
    @JsonProperty("type") val type: TaskEventType,
    @JsonProperty("teamId") val teamId: String,
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
          teamId = event.teamId.get(),
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

    fun to(): TaskEvent {
      when (this.type) {
        TaskEventType.TASK_CREATED -> {
          return TaskCreated(
            taskId = TaskId.from(value = this.taskId),
            type = TaskEventType.TASK_CREATED,
            teamId = TeamId.from(value = this.taskId),
            operator = ActorId.from(value = this.operator),
            title = Title.of(value = this.title),
            description = this.description?.let { Description.of(value = it) },
            assigner = this.assigner?.let { ActorId.from(value = it) },
            assignee = this.assignee?.let { ActorId.from(value = it) },
            fromTime = this.fromTime?.let { FromTime.of(value = it) },
            toTime = this.toTime?.let { ToTime.of(value = it) },
            occurredAt = this.occurredAt,
            completed = this.completed,
            version = this.version,
          )
        }

        else -> {
          throw RuntimeException()
        }
      }
    }
  }
