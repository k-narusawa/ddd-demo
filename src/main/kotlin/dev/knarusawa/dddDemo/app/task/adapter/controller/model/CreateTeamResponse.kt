package dev.knarusawa.dddDemo.app.task.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateTeamResponse(
  @JsonProperty("team_id") val teamId: String,
  @JsonProperty("name") val name: String,
)
