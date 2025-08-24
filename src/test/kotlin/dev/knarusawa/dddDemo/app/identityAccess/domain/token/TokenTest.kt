package dev.knarusawa.dddDemo.app.identityAccess.domain.token

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@DisplayName("ドメイン_集約_トークン")
class TokenTest {
  companion object {
    private const val ACCESS_TOKEN_SECRET = "access_token_secret"
    private const val ACCESS_TOKEN_EXPIRES_IN = 3600L
    private const val REFRESH_TOKEN_SECRET = "refresh_token_secret"
    private const val REFRESH_TOKEN_EXPIRES_IN = 86400L
  }

  @Nested
  @DisplayName("インスタンス化")
  inner class Instantiation {
    @Test
    @DisplayName("ofメソッドでインスタンスを生成できる")
    fun `can instantiate with of method`() {
      val userId = UserId.new()

      val sut =
        Token.of(
          userId = userId,
          accessTokenSecret = ACCESS_TOKEN_SECRET,
          accessTokenExpiresIn = ACCESS_TOKEN_EXPIRES_IN,
          refreshTokenSecret = REFRESH_TOKEN_SECRET,
          refreshTokenExpiresIn = REFRESH_TOKEN_EXPIRES_IN,
        )

      assertNotNull(sut.tokenId)
      assertEquals(userId, sut.userId)
      assertNotNull(sut.getAccessToken())
      assertNotNull(sut.getRefreshToken())
    }
  }

  @Nested
  @DisplayName("トークンの失効")
  inner class Revocation {
    @Test
    @DisplayName("revokeメソッドでトークンを失効できる")
    fun `can revoke token`() {
      val userId = UserId.new()
      val token =
        Token.of(
          userId = userId,
          accessTokenSecret = ACCESS_TOKEN_SECRET,
          accessTokenExpiresIn = ACCESS_TOKEN_EXPIRES_IN,
          refreshTokenSecret = REFRESH_TOKEN_SECRET,
          refreshTokenExpiresIn = REFRESH_TOKEN_EXPIRES_IN,
        )

      token.revoke()

      val isRevokedField = token.javaClass.getDeclaredField("isRevoked")
      isRevokedField.isAccessible = true
      val isRevoked = isRevokedField.get(token) as Boolean
      assertTrue(isRevoked)
    }
  }

  @Nested
  @DisplayName("有効期限")
  inner class Expiration {
    @Test
    @DisplayName("getExpiresInで正しい秒数を取得できる")
    fun `can get correct expiration`() {
      val userId = UserId.new()
      val now = LocalDateTime.now()
      val expiresIn = 3600L
      val token =
        Token.of(
          userId = userId,
          accessTokenSecret = ACCESS_TOKEN_SECRET,
          accessTokenExpiresIn = expiresIn,
          refreshTokenSecret = REFRESH_TOKEN_SECRET,
          refreshTokenExpiresIn = REFRESH_TOKEN_EXPIRES_IN,
        )
      val accessTokenExpirationField =
        token.javaClass.getDeclaredField(
          "accessTokenExpiration",
        )
      accessTokenExpirationField.isAccessible = true
      accessTokenExpirationField.set(token, now.plusSeconds(expiresIn))

      val expiresInResult = token.getExpiresIn()

      assertTrue(expiresInResult in (expiresIn - 2)..(expiresIn))
    }
  }
}
