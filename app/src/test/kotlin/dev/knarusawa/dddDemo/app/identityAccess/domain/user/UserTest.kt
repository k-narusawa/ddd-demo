package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.LoginFailed
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UsernameChanged
import dev.knarusawa.dddDemo.testFactory.TestUserFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
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
        TestUserFactory.createUserInstance(
          userId,
          Username.of("taro@example.com"),
        )
      val user2 =
        TestUserFactory.createUserInstance(
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
        TestUserFactory.createUserInstance(
          UserId.new(),
          Username.of("taro@example.com"),
        )
      val user2 =
        TestUserFactory.createUserInstance(
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
      val givenName = GivenName.of("名")
      val familyName = FamilyName.of("姓")
      val user = User.signup(username, password, givenName, familyName)

      Assertions.assertEquals(username, user.getUsername())
      Assertions.assertEquals(givenName, user.getGivenName())
      Assertions.assertEquals(familyName, user.getFamilyName())
    }
  }

  @Nested
  inner class ChangeUsername {
    @Test
    @DisplayName("ユーザー名を変更できる")
    fun user_can_change_username() {
      val user =
        TestUserFactory.createUserInstance(
          UserId.new(),
          Username.of("taro@example.com"),
        )
      val newUsername = Username.of("jiro@example.com")
      user.changeUsername(newUsername)

      assertEquals(newUsername, user.getUsername())
    }

    @Test
    @DisplayName("ユーザー名を変更した場合にイベントが発行される")
    fun when_user_change_username_publish_event() {
      val user =
        TestUserFactory.createUserInstance(
          UserId.new(),
          Username.of("taro@example.com"),
        )
      val newUsername = Username.of("jiro@example.com")

      user.changeUsername(newUsername)
      val events = user.getEvents()
      assertEquals(events.size, 1)
      assertInstanceOf<UsernameChanged>(events[0])
    }
  }

  @Nested
  inner class PasswordMatching {
    @Test
    @DisplayName("パスワードが一致する")
    fun user_can_verify_password() {
      val rawPassword = "!Password0"
      val password = Password.of(rawPassword)
      val givenName = GivenName.of("名")
      val familyName = FamilyName.of("姓")
      val user = User.signup(Username.of("taro@example.com"), password, givenName, familyName)

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
      val givenName = GivenName.of("名")
      val familyName = FamilyName.of("姓")
      val user = User.signup(Username.of("taro@example.com"), password, givenName, familyName)

      Assertions.assertThrows(LoginFailed::class.java) {
        user.verifyPassword(anotherRawPassword)
      }
    }
  }
}
