package dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRegistrationResponse(
  @JsonProperty("user_id") val userId: String,
  @JsonProperty("username") val username: String,
)
