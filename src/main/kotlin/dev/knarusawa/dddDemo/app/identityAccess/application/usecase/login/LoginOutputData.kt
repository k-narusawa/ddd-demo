package dev.knarusawa.dddDemo.app.identityAccess.application.usecase.login

import dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model.LoginResponse
import dev.knarusawa.dddDemo.app.identityAccess.domain.token.Token

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
