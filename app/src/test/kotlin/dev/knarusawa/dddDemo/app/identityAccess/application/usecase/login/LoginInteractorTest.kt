package dev.knarusawa.dddDemo.app.identityAccess.application.usecase.login

import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.LoginFailed
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Password
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Username
import dev.knarusawa.dddDemo.executionListener.DatabaseCleanupListener
import dev.knarusawa.dddDemo.testFactory.TestUserIntegrationFactory
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners

@SpringBootTest
@TestExecutionListeners(listeners = [DatabaseCleanupListener::class])
@DisplayName("ユースケース_ログイン")
class LoginInteractorTest
  @Autowired
  constructor(
    private val loginInteractor: LoginInteractor,
    private val testUserIntegrationFactory: TestUserIntegrationFactory,
  ) {
    @Nested
    inner class UseCaseTest {
      @Test
      @DisplayName("正しい認証情報でトークンが払い出されること")
      fun token_dispensed_with_correct_credentials() =
        runBlocking {
          testUserIntegrationFactory.createUser(
            username = Username.of("test@example.com"),
            password = Password.of("password"),
          )
          val input =
            LoginInputData.of(
              username = "test@example.com",
              password = "password",
              userAgent = "test",
              remoteAddr = "192.168.0.1",
            )

          val sut = loginInteractor.handle(input = input)

          assertNotNull(sut.response.accessToken)
          assertNotNull(sut.response.refreshToken)
          assertNotNull(sut.response.expiresIn)
        }

      @Test
      @DisplayName("間違った認証情報の場合に例外が投げられること")
      fun exception_thrown_in_case_of_wrong_credentials() {
        testUserIntegrationFactory.createUser(
          username = Username.of("test@example.com"),
          password = Password.of("password"),
        )
        val input =
          LoginInputData.of(
            username = "test@example.com",
            password = "incorrect_password",
            userAgent = "test",
            remoteAddr = "192.168.0.1",
          )

        Assertions.assertThrows(LoginFailed::class.java) {
          runBlocking { loginInteractor.handle(input = input) }
        }
      }
    }
  }
