package dev.knarusawa.dddDemo.app.identityAccess.domain.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.TokenUnauthorized
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import dev.knarusawa.dddDemo.util.logger
import jakarta.persistence.Embeddable
import java.util.Date

@Embeddable
data class AccessToken private constructor(
  private val value: String,
  @Transient()
  private val userId: UserId,
) {
  companion object {
    private val log = logger()

    fun generate(
      tokenId: TokenId,
      userId: UserId,
      secret: String,
      expiresIn: Long,
    ): AccessToken {
      val now = Date()
      val expiration = Date(now.time + (expiresIn * 1000))
      val algorithm = Algorithm.HMAC256(secret)
      val tokenString =
        JWT
          .create()
          .withJWTId(tokenId.get())
          .withSubject(userId.get())
          .withExpiresAt(expiration)
          .sign(algorithm)
      return AccessToken(value = tokenString, userId = userId)
    }

    fun fromJwt(
      jwt: String,
      secret: String,
    ): AccessToken {
      try {
        val algorithm = Algorithm.HMAC256(secret)
        val verifier =
          JWT
            .require(algorithm)
            .build()
        val decoded = verifier.verify(jwt)
        return AccessToken(value = jwt, userId = UserId.from(value = decoded.subject))
      } catch (ex: JWTVerificationException) {
        log.warn("アクセストークンの検証に失敗", ex)
        throw TokenUnauthorized(cause = ex)
      }
    }
  }

  fun get() = value
}
