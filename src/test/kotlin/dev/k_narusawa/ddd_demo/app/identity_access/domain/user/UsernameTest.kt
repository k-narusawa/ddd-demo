package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UsernameTest {
  @Nested
  @DisplayName("インスタンス化")
  inner class Instantiation {
    @Test
    @DisplayName("有効なメールアドレスでインスタンスを生成できる")
    fun `can instantiate with valid email`() {
      val email = "test@example.com"
      val username = Username.of(email)
      assertEquals(email, username.get())
    }

    @Test
    @DisplayName("無効なメールアドレスでインスタンスを生成しようとすると例外がスローされる")
    fun `throws exception for invalid email`() {
      val invalidEmail = "invalid-email"
      assertThrows<IllegalArgumentException> {
        Username.of(invalidEmail)
      }
    }
  }

  @Nested
  @DisplayName("等価性")
  inner class Equality {
    @Test
    @DisplayName("同じ値を持つインスタンスは等価である")
    fun `instances with same value are equal`() {
      val username1 = Username.of("test@example.com")
      val username2 = Username.of("test@example.com")

      assertEquals(username1, username2)
      assertEquals(username1.hashCode(), username2.hashCode())
    }

    @Test
    @DisplayName("異なる値を持つインスタンスは等価ではない")
    fun `instances with different values are not equal`() {
      val username1 = Username.of("test1@example.com")
      val username2 = Username.of("test2@example.com")

      assertNotEquals(username1, username2)
    }
  }
}
