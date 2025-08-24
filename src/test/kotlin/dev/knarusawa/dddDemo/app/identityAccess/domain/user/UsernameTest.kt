package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UsernameTest {
  @Nested
  @DisplayName("正常値テスト")
  inner class Normal {
    @ParameterizedTest
    @DisplayName("正常系")
    @ValueSource(
      strings = [
        "test@exmaple.com",
        "test01@example.com",
      ],
    )
    fun should_be_valid_username(username: String) {
      assertDoesNotThrow { Username.of(username) }
    }
  }

  @Nested
  @DisplayName("境界値テスト")
  inner class BoundaryValue {
    @DisplayName("正常系")
    @ParameterizedTest
    @ValueSource(
      strings = [
        "1234567890123456789012345678901234567890123456789012345678901234" + "@example.com",
      ],
    )
    fun should_be_accepted(username: String) {
      assertDoesNotThrow { Username.of(username) }
    }

    @DisplayName("異常系")
    @ParameterizedTest
    @ValueSource(
      strings = [
        "()@example.com",
      ],
    )
    fun should_be_rejected(username: String) {
      assertThrows(IllegalArgumentException::class.java) { Username.of(username) }
    }
  }

  @Nested
  @DisplayName("異常値テスト")
  inner class Abnormal {
    @DisplayName("異常系")
    @ParameterizedTest
    @ValueSource(
      strings = [
        "08012345678",
      ],
    )
    fun should_be_rejected(username: String) {
      assertThrows(IllegalArgumentException::class.java) { Username.of(username) }
    }
  }

  @Nested
  @DisplayName("極端値テスト")
  inner class ExtremeValue {
    @DisplayName("異常系")
    @Test
    fun should_be_rejected() {
      val username = "x".repeat(10000000) + "example.com"
      assertThrows(IllegalArgumentException::class.java) { Username.of(username) }
    }
  }

  @Nested
  @DisplayName("等価性")
  inner class Equality {
    @Test
    @DisplayName("同じ値を持つインスタンスは等価である")
    fun instances_with_same_value_are_equal() {
      val username1 = Username.of("test@example.com")
      val username2 = Username.of("test@example.com")

      assertEquals(username1, username2)
      assertEquals(username1.hashCode(), username2.hashCode())
    }

    @Test
    @DisplayName("異なる値を持つインスタンスは等価ではない")
    fun instances_with_different_values_are_not_equal() {
      val username1 = Username.of("test1@example.com")
      val username2 = Username.of("test2@example.com")

      assertNotEquals(username1, username2)
    }
  }
}
