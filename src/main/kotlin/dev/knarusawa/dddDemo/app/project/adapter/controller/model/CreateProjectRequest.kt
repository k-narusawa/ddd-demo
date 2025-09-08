package dev.knarusawa.dddDemo.app.project.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateProjectRequest(
  @JsonProperty("name") val name: String,
)
