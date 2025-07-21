package dev.k_narusawa.ddd_demo.identity_access.domain.user

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Password
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

class UserTest {
  @Nested
  inner class Identifier {
    @Test
    @DisplayName("同じIDを持つUserは等価である")
    fun `test equals and hashCode with same ID`() {
      val userId = UserId.new()
      val user1 = createUserInstance(userId, "Taro")
      val user2 = createUserInstance(userId, "Jiro")

      assertEquals(user1, user2, "同じIDを持つUserは等価であるべき")
      assertEquals(user1.hashCode(), user2.hashCode(), "同じIDを持つUserのhashCodeは同じであるべき")
    }

    @Test
    @DisplayName("異なるIDを持つUserは等価ではない")
    fun `test equals with different IDs`() {
      val user1 = createUserInstance(UserId.new(), "Taro")
      val user2 = createUserInstance(UserId.new(), "Taro") // 名前は同じ

      assertNotEquals(user1, user2, "異なるIDを持つUserは等価ではないべき")
    }
  }

  @Nested
  inner class Registration {
    @Test
    @DisplayName("ユーザー登録ができる")
    fun `test register`() {
      val username = Username.of("Taro")
      val passwordString = "!Password0"
      val password = Password.of(passwordString)
      val user = User.register(username, password)

      assertEquals(username, user.getUsername(), "ユーザー名が正しく設定されているべき")
      assertTrue(user.verifyPassword(passwordString), "パスワードが正しく設定されているべき")
    }
  }

  @Test
  @DisplayName("ユーザー名を変更できる")
  fun `test changeUsername`() {
    val user = createUserInstance(UserId.new(), "Taro")
    val newUsername = Username.of("Jiro")
    user.changeUsername(newUsername)

    assertEquals(newUsername, user.getUsername(), "ユーザー名が正しく変更されているべき")
  }

  @Nested
  inner class PasswordMatching {
    @Test
    @DisplayName("パスワードが一致する")
    fun `test password matches`() {
      val rawPassword = "!Password0"
      val password = Password.of(rawPassword)
      val user = User.register(Username.of("Taro"), password)

      assertTrue(user.verifyPassword(rawPassword), "同じパスワードであればtrueを返すべき")
    }

    @Test
    @DisplayName("パスワードが一致しない")
    fun `test password does not match`() {
      val rawPassword = "!Password0"
      val anotherRawPassword = "!Password1"
      val password = Password.of(rawPassword)
      val user = User.register(Username.of("Taro"), password)

      assertFalse(
        user.verifyPassword(anotherRawPassword),
        "異なるパスワードであればfalseを返すべき"
      )
    }
  }

  private fun createUserInstance(id: UserId, name: String): User {
    val constructor = User::class.primaryConstructor!!
    constructor.isAccessible = true
    return constructor.call(id, Username.of(name), Password.of("!Password0"))
  }
}
