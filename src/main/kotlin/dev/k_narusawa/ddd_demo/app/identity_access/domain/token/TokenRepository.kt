package dev.k_narusawa.ddd_demo.app.identity_access.domain.token

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository : JpaRepository<Token, TokenId> {
    fun save(token: Token)

    fun findByUserId(userId: UserId): List<Token>

    fun findByAccessToken(accessToken: AccessToken): Token?
}
