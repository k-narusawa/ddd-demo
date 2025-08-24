package dev.k_narusawa.ddd_demo.app.task.application.dto

import dev.k_narusawa.ddd_demo.app.task.adapter.http.model.IntrospectionResponse

data class IntrospectResultDto(
    val sub: String?,
    val active: Boolean,
) {
    companion object {
        fun of(resp: IntrospectionResponse) =
            IntrospectResultDto(
                sub = resp.sub,
                active = resp.active,
            )
    }
}
