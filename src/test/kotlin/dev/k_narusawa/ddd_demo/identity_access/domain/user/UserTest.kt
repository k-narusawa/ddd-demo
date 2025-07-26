package dev.k_narusawa.ddd_demo.identity_access.domain.user

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.LoginAttempt
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Password
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.publisher.AuthenticationFailedEventPublisher
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.publisher.AuthenticationSuccessEventPublisher
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.publisher.ChangeUsernameEventPublisher
import dev.k_narusawa.ddd_demo.app.identity_access.exception.AuthenticationException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

@SpringBootTest(
  classes = kotlin.arrayOf(
    AuthenticationSuccessEventPublisher::class,
    AuthenticationFailedEventPublisher::class,
    ChangeUsernameEventPublisher::class
  )
)
class UserTest {
  @Nested
  inner class Identifier {
    @Test
    @DisplayName("同じUserIdを持つUserは等価である")
    fun `test equals and hashCode with same ID`() {
      val userId = UserId.new()
      val user1 = createUserInstance(
        userId,
        Username.of("Taro")
      )
      val user2 = createUserInstance(
        userId,
        Username.of("Jiro")
      )

      assertEquals(user1, user2, "同じIDを持つUserは等価であるべき")
      assertEquals(user1.hashCode(), user2.hashCode(), "同じIDを持つUserのhashCodeは同じであるべき")
    }

    @Test
    @DisplayName("異なるUserIdを持つUserは等価ではない")
    fun `test equals with different IDs`() {
      val user1 = createUserInstance(
        UserId.new(),
        Username.of("Taro")
      )
      val user2 = createUserInstance(
        UserId.new(),
        Username.of("Taro")
      )

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
      assertDoesNotThrow {
        user.verifyPassword(
          passwordString,
          "test-agent",
          "127.0.0.1"
        )
      }
    }
  }

  @Nested
  inner class ChangeUsername {
    @Test
    @DisplayName("ユーザー名を変更できる")
    fun `test changeUsername`() {
      val user = createUserInstance(
        UserId.new(),
        Username.of("Taro")
      )
      val newUsername = Username.of("Jiro")
      user.changeUsername(newUsername, "test-agent", "127.0.0.1")

      assertEquals(newUsername, user.getUsername(), "ユーザー名が正しく変更されているべき")
    }
  }

  @Nested
  inner class PasswordMatching {
    @Test
    @DisplayName("パスワードが一致する")
    fun `test password matches`() {
      val rawPassword = "!Password0"
      val password = Password.of(rawPassword)
      val user = User.register(Username.of("Taro"), password)
      createUserInstance()

      assertDoesNotThrow {
        user.verifyPassword(
          rawPassword,
          "test-agent",
          "127.0.0.1"
        )
      }
    }

    @Test
    @DisplayName("パスワードが一致しない")
    fun `test password does not match`() {
      val rawPassword = "!Password0"
      val anotherRawPassword = "!Password1"
      val password = Password.of(rawPassword)
      val user = User.register(Username.of("Taro"), password)

      assertThrows(AuthenticationException::class.java) {
        user.verifyPassword(
          anotherRawPassword,
          "test-agent",
          "127.0.0.1"
        )
      }
    }
  }

  private fun createUserInstance(
    userId: UserId = UserId.new(),
    username: Username = Username.of("dummy"),
    password: Password = Password.of("dummy")
  ): User {
    val constructor = User::class.primaryConstructor!!
    constructor.isAccessible = true
    return constructor.call(
      userId,
      username,
      password,
      LoginAttempt.new(userId = userId),
      1L,
    )
  }
}
