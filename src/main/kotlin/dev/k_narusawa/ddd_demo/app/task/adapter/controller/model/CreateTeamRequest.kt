package dev.k_narusawa.ddd_demo.app.task.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateTeamRequest(
  @JsonProperty("name") val name: String,
)
