package dev.k_narusawa.ddd_demo.app.identity_access.domain.token

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.Duration
import java.time.LocalDateTime

@Entity
@Table(name = "ddd_token")
class Token private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("token_id"))
  val tokenId: TokenId,

  @AttributeOverride(name = "value", column = Column("user_id"))
  val userId: UserId,

  @AttributeOverride(name = "value", column = Column("access_token"))
  private var accessToken: AccessToken,

  @AttributeOverride(name = "value", column = Column("refresh_token"))
  private var refreshToken: RefreshToken,

  @AttributeOverride(name = "value", column = Column("access_token_expiration"))
  private var accessTokenExpiration: LocalDateTime,

  @AttributeOverride(name = "value", column = Column("refresh_token_expiration"))
  private var refreshTokenExpiration: LocalDateTime,

  @AttributeOverride(name = "value", column = Column("is_revoked"))
  private var isRevoked: Boolean,

  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,
) {
  companion object {
    fun of(
      userId: UserId,
      accessTokenSecret: String,
      accessTokenExpiresIn: Long,
      refreshTokenSecret: String,
      refreshTokenExpiresIn: Long,
    ): Token {
      return Token(
        tokenId = TokenId.generate(),
        userId = userId,
        accessToken = AccessToken.generate(
          userId = userId,
          secret = accessTokenSecret,
          expiresIn = accessTokenExpiresIn
        ),
        refreshToken = RefreshToken.generate(
          userId = userId,
          secret = refreshTokenSecret,
          expiresIn = refreshTokenExpiresIn
        ),
        accessTokenExpiration = LocalDateTime.now().plusSeconds(accessTokenExpiresIn),
        refreshTokenExpiration = LocalDateTime.now().plusSeconds(refreshTokenExpiresIn),
        isRevoked = false
      )
    }
  }

  fun getAccessToken() = this.accessToken
  fun getRefreshToken() = this.refreshToken
  fun getExpiresIn(): Long {
    val duration = Duration.between(LocalDateTime.now(), this.accessTokenExpiration)
    return duration.toSeconds()
  }

  fun revoke() {
    this.isRevoked = true
  }
}
