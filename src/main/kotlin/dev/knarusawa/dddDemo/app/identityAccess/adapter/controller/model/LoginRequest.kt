package dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(
  @JsonProperty("username") val username: String,
  @JsonProperty("password") val password: String,
)
