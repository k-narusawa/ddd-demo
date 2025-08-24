package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.login

import dev.k_narusawa.ddd_demo.app.identity_access.adapter.controller.model.LoginResponse
import dev.k_narusawa.ddd_demo.app.identity_access.domain.token.Token

data class LoginOutputData private constructor(
    val response: LoginResponse,
) {
    companion object {
        fun of(token: Token): LoginOutputData =
            LoginOutputData(
                response =
                    LoginResponse(
                        accessToken = token.getAccessToken().get(),
                        refreshToken = token.getRefreshToken().get(),
                        expiresIn = token.getExpiresIn(),
                    ),
            )
    }
}
