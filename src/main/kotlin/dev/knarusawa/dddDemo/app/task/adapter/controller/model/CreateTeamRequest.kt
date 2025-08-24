package dev.knarusawa.dddDemo.app.task.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateTeamRequest(
  @JsonProperty("name") val name: String,
)
