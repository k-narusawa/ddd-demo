package dev.k_narusawa.ddd_demo.app.identity_access.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class IntrospectionResponse(
    @JsonProperty("sub") val sub: String?,
    @JsonProperty("active") val active: Boolean,
)
