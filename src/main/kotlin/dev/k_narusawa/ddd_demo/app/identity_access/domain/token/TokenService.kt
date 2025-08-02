package dev.k_narusawa.ddd_demo.app.identity_access.domain.token

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import org.springframework.core.env.Environment
import org.springframework.core.env.getProperty
import org.springframework.stereotype.Service

@Service
class TokenService(
  private val environment: Environment
) {
  fun generateToken(userId: UserId): Token {
    val accessTokenSecret =
      environment.getProperty<String>("environment.identity_access.access_token.secret")
    val accessTokenExpiresIn =
      environment.getProperty<Long>("environment.identity_access.access_token.expires_in")
    val refreshTokenSecret =
      environment.getProperty<String>("environment.identity_access.refresh_token.secret")
    val refreshTokenExpiresIn =
      environment.getProperty<Long>("environment.identity_access.refresh_token.expires_in")

    return Token.of(
      userId = userId,
      accessTokenSecret = accessTokenSecret!!,
      accessTokenExpiresIn = accessTokenExpiresIn!!,
      refreshTokenSecret = refreshTokenSecret!!,
      refreshTokenExpiresIn = refreshTokenExpiresIn!!
    )

  }
}
