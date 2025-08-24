package dev.k_narusawa.ddd_demo.app.identity_access.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponse(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("refresh_token") val refreshToken: String,
    @JsonProperty("expires_in") val expiresIn: Long,
)
