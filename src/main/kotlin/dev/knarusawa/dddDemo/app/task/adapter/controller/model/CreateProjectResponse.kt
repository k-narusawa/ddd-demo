package dev.knarusawa.dddDemo.app.task.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateProjectResponse(
  @JsonProperty("project_id") val projectId: String,
  @JsonProperty("name") val name: String,
)
