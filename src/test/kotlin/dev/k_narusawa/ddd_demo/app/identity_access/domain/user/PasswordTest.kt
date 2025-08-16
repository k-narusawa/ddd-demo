package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PasswordTest {
  @Nested
  @DisplayName("インスタンス化")
  inner class Instantiation {
    @Test
    @DisplayName("有効なパスワードでインスタンスを生成できる")
    fun `can instantiate with valid password`() {
      val rawPassword = "!Password0"
      val password = Password.of(rawPassword)
      assertNotNull(password)
      assertTrue(password.matches(rawPassword))
    }

    @Test
    @DisplayName("空のパスワードでインスタンスを生成しようとすると例外がスローされる")
    fun `throws exception for blank password`() {
      assertThrows<IllegalArgumentException> {
        Password.of("")
      }
    }

    @Test
    @DisplayName("8文字未満のパスワードでインスタンスを生成しようとすると例外がスローされる")
    fun `throws exception for password shorter than 8 characters`() {
      assertThrows<IllegalArgumentException> {
        Password.of("short")
      }
    }
  }

  @Nested
  @DisplayName("パスワード照合")
  inner class PasswordMatching {
    @Test
    @DisplayName("パスワードが一致する")
    fun `returns true for matching password`() {
      val rawPassword = "!Password0"
      val password = Password.of(rawPassword)
      assertTrue(password.matches(rawPassword))
    }

    @Test
    @DisplayName("パスワードが一致しない")
    fun `returns false for non-matching password`() {
      val rawPassword = "!Password0"
      val wrongPassword = "!Password1"
      val password = Password.of(rawPassword)
      assertFalse(password.matches(wrongPassword))
    }
  }

  @Nested
  @DisplayName("等価性")
  inner class Equality {
    @Test
    @DisplayName("同じ値で生成したインスタンスは等価")
    fun `instances created with same value are not equal`() {
      val rawPassword = "!Password0"
      val passwordA = Password.of(rawPassword)
      val passwordB = Password.of(rawPassword)

      assertEquals(passwordA, passwordB)
    }
  }
}
