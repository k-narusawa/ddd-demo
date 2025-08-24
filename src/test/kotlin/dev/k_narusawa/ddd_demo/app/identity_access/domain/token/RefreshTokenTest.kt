package dev.k_narusawa.ddd_demo.app.identity_access.domain.token

import dev.k_narusawa.ddd_demo.app.identity_access.domain.exception.TokenUnauthorized
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("ドメイン_値オブジェクト_リフレッシュトークン")
class RefreshTokenTest {
    companion object {
        private const val SECRET = "secret"
        private const val EXPIRES_IN = 3600L
    }

    @Nested
    @DisplayName("インスタンス化")
    inner class Instantiation {
        @Test
        @DisplayName("generateメソッドでインスタンスを生成できる")
        fun `can instantiate with generate method`() {
            val tokenId = TokenId.generate()
            val userId = UserId.new()

            val sut =
                RefreshToken.generate(
                    tokenId = tokenId,
                    userId = userId,
                    secret = SECRET,
                    expiresIn = EXPIRES_IN,
                )

            assertNotNull(sut.get())
        }

        @Test
        @DisplayName("fromJwtメソッドでインスタンスを生成できる")
        fun `can instantiate with fromJwt method`() {
            val userId = UserId.new()
            val token =
                RefreshToken.generate(
                    tokenId = TokenId.generate(),
                    userId = userId,
                    secret = SECRET,
                    expiresIn = EXPIRES_IN,
                )

            val sut = RefreshToken.fromJwt(token.get(), SECRET)

            assertNotNull(sut)
        }

        @Test
        @DisplayName("不正なJWTでfromJwtを呼び出すと例外がスローされる")
        fun `fromJwt with invalid jwt throws exception`() {
            assertThrows(TokenUnauthorized::class.java) {
                RefreshToken.fromJwt("invalid_jwt", SECRET)
            }
        }
    }

    @Nested
    @DisplayName("等価性")
    inner class Equality {
        @Test
        @DisplayName("同じ値を持つインスタンスは等価である")
        fun `instances with same value are equal`() {
            val userId = UserId.new()
            val token =
                RefreshToken.generate(
                    tokenId = TokenId.generate(),
                    userId = userId,
                    secret = SECRET,
                    expiresIn = EXPIRES_IN,
                )

            val sut1 = RefreshToken.fromJwt(token.get(), SECRET)
            val sut2 = RefreshToken.fromJwt(token.get(), SECRET)

            assertEquals(sut1, sut2)
            assertEquals(sut1.hashCode(), sut2.hashCode())
        }

        @Test
        @DisplayName("異なる値を持つインスタンスは等価ではない")
        fun `instances with different values are not equal`() {
            val userId = UserId.new()
            val token1 =
                RefreshToken.generate(
                    tokenId = TokenId.generate(),
                    userId = userId,
                    secret = SECRET,
                    expiresIn = EXPIRES_IN,
                )
            val token2 =
                RefreshToken.generate(
                    tokenId = TokenId.generate(),
                    userId = userId,
                    secret = SECRET,
                    expiresIn = EXPIRES_IN,
                )

            val sut1 = RefreshToken.fromJwt(token1.get(), SECRET)
            val sut2 = RefreshToken.fromJwt(token2.get(), SECRET)

            assertNotEquals(sut1, sut2)
        }
    }
}
