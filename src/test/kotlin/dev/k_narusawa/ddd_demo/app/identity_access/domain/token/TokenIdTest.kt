
package dev.k_narusawa.ddd_demo.app.identity_access.domain.token

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

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
