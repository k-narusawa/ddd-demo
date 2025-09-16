package dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class IntrospectionResponse(
  @JsonProperty("sub") val sub: String?,
  @JsonProperty("active") val active: Boolean,
)
