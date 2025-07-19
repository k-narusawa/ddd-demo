package dev.k_narusawa.ddd_demo.http.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRegistrationRequest(
  @JsonProperty("username") val username: String,
  @JsonProperty("password") val password: String
)