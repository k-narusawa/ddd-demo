package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.introspection

import dev.k_narusawa.ddd_demo.app.identity_access.domain.token.AccessToken

data class IntrospectionInputData private constructor(
    val accessToken: AccessToken,
) {
    companion object {
        fun of(
            accessToken: String,
            secret: String,
        ) = IntrospectionInputData(
            accessToken = AccessToken.fromJwt(jwt = accessToken, secret = secret),
        )
    }
}
