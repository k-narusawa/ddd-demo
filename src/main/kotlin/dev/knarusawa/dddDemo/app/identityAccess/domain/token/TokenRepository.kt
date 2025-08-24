package dev.knarusawa.dddDemo.app.identityAccess.domain.token

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository : JpaRepository<Token, TokenId> {
  fun save(token: Token)

  fun findByUserId(userId: UserId): List<Token>

  fun findByAccessToken(accessToken: AccessToken): Token?
}
