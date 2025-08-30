package dev.knarusawa.dddDemo.app.task.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class TaskResponse(
  @JsonProperty("task_id") val taskId: String,
  @JsonProperty("team_id") val teamId: String,
  @JsonProperty("operator") val operator: String,
  @JsonProperty("title") val title: String,
  @JsonProperty("description") val description: String?,
  @JsonProperty("assigner") val assigner: String?,
  @JsonProperty("assignee") val assignee: String?,
  @JsonProperty("from_time") val fromTime: LocalDateTime?,
  @JsonProperty("to_time") val toTime: LocalDateTime?,
)
