package dev.knarusawa.dddDemo.app.project.adapter.gateway.http.model

import com.fasterxml.jackson.annotation.JsonProperty

data class IntrospectionResponse(
  @JsonProperty("sub") val sub: String?,
  @JsonProperty("active") val active: Boolean,
)
