package dev.knarusawa.dddDemo.app.task.domain.task.event

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import dev.knarusawa.dddDemo.app.task.domain.TaskEventType
import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId
import java.time.LocalDateTime

data class TaskCompleted
  @JsonCreator
  constructor(
    @JsonProperty("taskId") override val taskId: TaskId,
    @JsonProperty("type") override val type: TaskEventType,
    @JsonProperty("teamId") override val teamId: TeamId,
    @JsonProperty("operator") override val operator: ActorId,
    @JsonProperty("title") override val title: Title,
    @JsonProperty("description") override val description: Description?,
    @JsonProperty("assigner") override val assigner: ActorId?,
    @JsonProperty("assignee") override val assignee: ActorId?,
    @JsonProperty("fromTime") override val fromTime: FromTime?,
    @JsonProperty("toTime") override val toTime: ToTime?,
    @JsonProperty("occurredAt") override val occurredAt: LocalDateTime = LocalDateTime.now(),
    @JsonProperty("published") override val published: Boolean = false,
    @JsonProperty("version") override val version: Long,
  ) : TaskEvent(
      source = taskId,
    ) {
    companion object {
      fun of(
        taskId: TaskId,
        teamId: TeamId,
        operator: ActorId,
        title: Title,
        description: Description?,
        assigner: ActorId?,
        assignee: ActorId?,
        fromTime: FromTime?,
        toTime: ToTime?,
        version: Long,
      ) = TaskCompleted(
        taskId = taskId,
        teamId = teamId,
        operator = operator,
        type = TaskEventType.TASK_COMPLETED,
        title = title,
        description = description,
        assigner = assigner,
        assignee = assignee,
        fromTime = fromTime,
        toTime = toTime,
        occurredAt = LocalDateTime.now(),
        published = false,
        version = version,
      )
    }
  }
