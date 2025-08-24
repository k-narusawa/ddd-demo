package dev.knarusawa.dddDemo.app.identityAccess.application.usecase.introspection

import dev.knarusawa.dddDemo.app.identityAccess.domain.token.AccessToken

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
