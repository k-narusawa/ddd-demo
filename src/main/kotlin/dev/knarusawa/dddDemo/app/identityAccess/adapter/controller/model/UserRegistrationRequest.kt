package dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRegistrationRequest(
  @JsonProperty("username") val username: String,
  @JsonProperty("password") val password: String,
  @JsonProperty("given_name") val givenName: String,
  @JsonProperty("family_name") val familyName: String,
)
