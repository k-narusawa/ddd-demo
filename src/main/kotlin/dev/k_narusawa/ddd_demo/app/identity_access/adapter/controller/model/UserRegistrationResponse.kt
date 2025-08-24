package dev.k_narusawa.ddd_demo.app.identity_access.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRegistrationResponse(
    @JsonProperty("user_id") val userId: String,
    @JsonProperty("username") val username: String,
)
