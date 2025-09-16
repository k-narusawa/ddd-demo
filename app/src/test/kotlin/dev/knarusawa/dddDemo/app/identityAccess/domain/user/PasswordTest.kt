package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("ドメイン_値オブジェクト_パスワード")
class PasswordTest {
  @Nested
  @DisplayName("正常値テスト")
  inner class Normal {
    @ParameterizedTest
    @DisplayName("有効なパスワードでインスタンスを生成できる")
    @ValueSource(
      strings = [
        "password123",
        "12345678",
        "!@#$%^&*",
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
      ],
    )
    fun should_be_valid_password(rawPassword: String) {
      val password = Password.of(rawPassword)
      assertNotNull(password)
      assertTrue(password.matches(rawPassword))
    }
  }

  @Nested
  @DisplayName("境界値テスト")
  inner class BoundaryValue {
    @Test
    @DisplayName("8文字のパスワードでインスタンスを生成できる")
    fun should_be_accepted() {
      val rawPassword = "a".repeat(8)
      val password = Password.of(rawPassword)
      assertNotNull(password)
      assertTrue(password.matches(rawPassword))
    }

    @Test
    @DisplayName("7文字のパスワードでインスタンスを生成しようとすると例外がスローされる")
    fun should_be_rejected() {
      val rawPassword = "a".repeat(7)
      assertThrows<IllegalArgumentException> {
        Password.of(rawPassword)
      }
    }
  }

  @Nested
  @DisplayName("異常値テスト")
  inner class Abnormal {
    @ParameterizedTest
    @DisplayName("無効なパスワードでインスタンスを生成しようとすると例外がスローされる")
    @ValueSource(
      strings = [
        "short",
        " ",
        "",
      ],
    )
    fun should_be_rejected(rawPassword: String) {
      assertThrows<IllegalArgumentException> {
        Password.of(rawPassword)
      }
    }
  }

  @Nested
  @DisplayName("極端値テスト")
  inner class ExtremeValue {
    @Test
    @DisplayName("非常に長いパスワードでインスタンスを生成できる")
    fun should_be_accepted() {
      val rawPassword = "a".repeat(1000)
      val password = Password.of(rawPassword)
      assertNotNull(password)
      assertTrue(password.matches(rawPassword))
    }
  }

  @Nested
  @DisplayName("パスワード照合")
  inner class PasswordMatching {
    @Test
    @DisplayName("パスワードが一致する")
    fun returns_true_for_matching_password() {
      val rawPassword = "!Password0"
      val password = Password.of(rawPassword)
      assertTrue(password.matches(rawPassword))
    }

    @Test
    @DisplayName("パスワードが一致しない")
    fun returns_false_for_non_matching_password() {
      val rawPassword = "!Password0"
      val wrongPassword = "!Password1"
      val password = Password.of(rawPassword)
      assertFalse(password.matches(wrongPassword))
    }
  }
}
