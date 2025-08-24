package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.AccountLock
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.LoginFailed
import dev.knarusawa.dddDemo.app.identityAccess.domain.loginAttempt.LoginAttemptRepository
import dev.knarusawa.dddDemo.executionListener.DatabaseCleanupListener
import dev.knarusawa.dddDemo.testFactory.TestUserFactory
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners

@SpringBootTest
@TestExecutionListeners(listeners = [DatabaseCleanupListener::class])
@DisplayName("ドメインサービス_UserService")
class UserServiceTest
  @Autowired
  constructor(
    private val userRepository: UserRepository,
    private val loginAttemptRepository: LoginAttemptRepository,
    private val sut: UserService,
    private val testUserFactory: TestUserFactory,
  ) {
    @Nested
    @DisplayName("isExistsメソッド")
    inner class IsExists {
      @Test
      @DisplayName("ユーザーが存在する場合、falseを返す")
      fun is_exists_should_return_false_when_user_exists() {
        val username = Username.of("test@example.com")
        testUserFactory.createUser(username = username, password = Password.of("password"))

        val result = sut.isExists(username)

        assertFalse(result)
      }

      @Test
      @DisplayName("ユーザーが存在しない場合、trueを返す")
      fun is_exists_should_return_true_when_user_does_not_exist() {
        val username = Username.of("test@example.com")

        val result = sut.isExists(username)

        assertTrue(result)
      }
    }

    @Nested
    @DisplayName("loginメソッド")
    inner class Login {
      private val username = Username.of("test@example.com")
      private val password = Password.of("!Password0")
      private val userAgent = "test-agent"
      private val ipAddress = "127.0.0.1"

      @Test
      @DisplayName("認証情報が正しい場合、トークンを返す")
      fun login_should_return_token_when_credentials_are_valid() {
        testUserFactory.createUser(username = username, password = password)

        val token = sut.login(username, "!Password0", userAgent, ipAddress)

        assertNotNull(token)
        assertNotNull(token.getAccessToken())
        assertNotNull(token.getRefreshToken())
      }

      @Test
      @DisplayName("ユーザーが見つからない場合、LoginFailed例外をスローする")
      fun login_should_throw_login_failed_when_user_not_found() {
        assertThrows<LoginFailed> {
          sut.login(username, "password", userAgent, ipAddress)
        }
      }

      @Test
      @DisplayName("パスワードが不正な場合、LoginFailed例外をスローする")
      fun login_should_throw_login_failed_when_password_is_incorrect() {
        testUserFactory.createUser(username = username, password = password)

        assertThrows<LoginFailed> {
          sut.login(username, "wrong-password", userAgent, ipAddress)
        }
      }

      @Test
      @DisplayName("パスワードを5回間違えるとアカウントがロックされる")
      fun login_should_throw_account_lock_when_password_is_incorrect_5_times() {
        val user = testUserFactory.createUser(username = username, password = password)

        repeat(4) {
          assertThrows<LoginFailed> {
            sut.login(username, "wrong-password", userAgent, ipAddress)
          }
        }

        assertThrows<AccountLock> {
          sut.login(username, "wrong-password", userAgent, ipAddress)
        }
        val loginAttempt = loginAttemptRepository.findByUserId(user.userId)
        assertTrue(loginAttempt!!.isLocked())
      }
    }
  }
