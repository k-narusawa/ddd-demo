package dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRegistrationRequest(
  @JsonProperty("username") val username: String,
  @JsonProperty("password") val password: String,
  @JsonProperty("personal_name") val personalName: String,
)
