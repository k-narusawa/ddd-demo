package dev.knarusawa.dddDemo.app.identityAccess.domain.token

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull

@DisplayName("ドメイン_値オブジェクト_トークンID")
class TokenIdTest {
  @Nested
  @DisplayName("インスタンス化")
  inner class Instantiation {
    @Test
    @DisplayName("generateメソッドでインスタンスを生成できる")
    fun `can instantiate with generate method`() {
      val sut = TokenId.generate()

      assertNotNull(sut)
    }
  }
}
