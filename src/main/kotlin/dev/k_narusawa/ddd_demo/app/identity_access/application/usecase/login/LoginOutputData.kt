package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.login

import dev.k_narusawa.ddd_demo.app.identity_access.domain.token.Token
import dev.k_narusawa.ddd_demo.http.model.LoginResponse

data class LoginOutputData private constructor(
  val response: LoginResponse
) {
  companion object {
    fun of(token: Token): LoginOutputData {
      return LoginOutputData(
        response = LoginResponse(
          accessToken = token.getAccessToken().get(),
          refreshToken = token.getRefreshToken().get(),
          expiresIn = token.getExpiresIn()
        )
      )
    }
  }
}
