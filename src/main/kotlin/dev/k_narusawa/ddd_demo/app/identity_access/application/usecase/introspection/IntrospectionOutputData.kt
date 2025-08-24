package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.introspection

import dev.k_narusawa.ddd_demo.app.identity_access.adapter.controller.model.IntrospectionResponse
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId

data class IntrospectionOutputData private constructor(
    val response: IntrospectionResponse,
) {
    companion object {
        fun of(
            sub: UserId? = null,
            active: Boolean,
        ) = IntrospectionOutputData(
            response =
                IntrospectionResponse(
                    sub = sub?.get(),
                    active = active,
                ),
        )
    }
}
