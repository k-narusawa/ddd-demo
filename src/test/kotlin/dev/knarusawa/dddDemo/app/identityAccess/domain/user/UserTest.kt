package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.LoginFailed
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UsernameChangedEvent
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible
import kotlin.test.assertEquals

@DisplayName("ドメイン_集約_ユーザ")
class UserTest {
  companion object {
    private const val DUMMY_UA = "dummy_agent"
    private const val DUMMY_IP = "127.0.0.1"
  }

  @Nested
  inner class Identifier {
    @Test
    @DisplayName("同じUserIdを持つUserは等価である")
    fun test_equals_and_hashCode_with_same_ID() {
      val userId = UserId.new()
      val user1 =
        createUserInstance(
          userId,
          Username.of("taro@example.com"),
        )
      val user2 =
        createUserInstance(
          userId,
          Username.of("jiro@example.com"),
        )

      Assertions.assertEquals(user1, user2)
      Assertions.assertEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    @DisplayName("異なるUserIdを持つUserは等価ではない")
    fun test_equals_with_different_IDs() {
      val user1 =
        createUserInstance(
          UserId.new(),
          Username.of("taro@example.com"),
        )
      val user2 =
        createUserInstance(
          UserId.new(),
          Username.of("taro@example.com"),
        )

      Assertions.assertNotEquals(user1, user2)
    }
  }

  @Nested
  inner class Registration {
    @Test
    @DisplayName("ユーザー登録ができる")
    fun user_can_create_new_account() {
      val username = Username.of("taro@example.com")
      val passwordString = "!Password0"
      val password = Password.of(passwordString)
      val user = User.signup(username, password, "テスト氏名")

      Assertions.assertEquals(username, user.getUsername())
    }
  }

  @Nested
  inner class ChangeUsername {
    @Test
    @DisplayName("ユーザー名を変更できる")
    fun user_can_change_username() {
      val user =
        createUserInstance(
          UserId.new(),
          Username.of("taro@example.com"),
        )
      val newUsername = Username.of("jiro@example.com")
      user.changeUsername(newUsername, DUMMY_UA, DUMMY_IP)

      assertEquals(newUsername, user.getUsername())
    }

    @Test
    @DisplayName("ユーザー名を変更した場合にイベントが発行される")
    fun when_user_change_username_publish_event() {
      val user =
        createUserInstance(
          UserId.new(),
          Username.of("taro@example.com"),
        )
      val newUsername = Username.of("jiro@example.com")

      user.changeUsername(newUsername, DUMMY_UA, DUMMY_IP)
      val events = user.getEvents()
      assertEquals(events.size, 1)
      assertInstanceOf<UsernameChangedEvent>(events[0])
    }
  }

  @Nested
  inner class PasswordMatching {
    @Test
    @DisplayName("パスワードが一致する")
    fun user_can_verify_password() {
      val rawPassword = "!Password0"
      val password = Password.of(rawPassword)
      val user = User.signup(Username.of("taro@example.com"), password, "テスト氏名")

      Assertions.assertDoesNotThrow {
        user.verifyPassword(rawPassword)
      }
    }

    @Test
    @DisplayName("パスワードが一致しない場合に例外が投げられる")
    fun when_user_mistake_password_throw_exception() {
      val rawPassword = "!Password0"
      val anotherRawPassword = "!Password1"
      val password = Password.of(rawPassword)
      val user = User.signup(Username.of("taro@example.com"), password, "テスト氏名")

      Assertions.assertThrows(LoginFailed::class.java) {
        user.verifyPassword(anotherRawPassword)
      }
    }
  }

  private fun createUserInstance(
    userId: UserId = UserId.new(),
    username: Username = Username.of("dummy@example.com"),
    password: Password = Password.of("!Password0"),
  ): User {
    val constructor = User::class.primaryConstructor!!
    constructor.isAccessible = true
    return constructor.call(
      userId,
      username,
      password,
      LoginFailureCount.init(),
      LastLoginFailedAt.init(),
      AccountStatus.NORMAL,
      1L,
      mutableListOf<IdentityAccessEvent>(),
    )
  }
}
