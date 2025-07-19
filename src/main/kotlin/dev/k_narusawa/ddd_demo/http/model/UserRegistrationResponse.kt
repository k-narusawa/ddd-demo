package dev.k_narusawa.ddd_demo.http.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRegistrationResponse(
  @JsonProperty("user_id") val userId: String,
  @JsonProperty("username") val username: String,
)
