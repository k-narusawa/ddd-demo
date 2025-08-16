package dev.k_narusawa.ddd_demo.app.identity_access.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRegistrationRequest(
  @JsonProperty("username") val username: String,
  @JsonProperty("password") val password: String
)
