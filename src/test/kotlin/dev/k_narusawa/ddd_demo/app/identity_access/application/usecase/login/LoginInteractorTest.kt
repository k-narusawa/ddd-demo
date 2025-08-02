package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.login

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Password
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserRepository
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username
import dev.k_narusawa.ddd_demo.app.identity_access.exception.AuthenticationException
import dev.k_narusawa.ddd_demo.executionListener.DatabaseCleanupListener
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
class LoginInteractorTest @Autowired constructor(
  private val userRepository: UserRepository,
  private val loginInteractor: LoginInteractor
) {
  @Nested
  inner class UseCaseTest {
    @Test
    @DisplayName("正しい認証情報でトークンが払い出されること")
    fun token_dispensed_with_correct_credentials() = runBlocking {
      createUser(username = "test@example.com", password = "password")
      val input = LoginInputData.of(
        username = "test@example.com",
        password = "password",
        userAgent = "test",
        remoteAddr = "192.168.0.1"
      )

      val sut = loginInteractor.handle(input = input)

      assertNotNull(sut.response.accessToken)
      assertNotNull(sut.response.refreshToken)
      assertNotNull(sut.response.expiresIn)
    }

    @Test
    @DisplayName("間違った認証情報の場合に例外が投げられること")
    fun exception_thrown_in_case_of_wrong_credentials() {
      createUser(username = "test@example.com", password = "password")
      val input = LoginInputData.of(
        username = "test@example.com",
        password = "incorrect_password",
        userAgent = "test",
        remoteAddr = "192.168.0.1"
      )

      Assertions.assertThrows(AuthenticationException::class.java) {
        runBlocking { loginInteractor.handle(input = input) }
      }
    }
  }

  private fun createUser(username: String, password: String) {
    val user = User.register(
      username = Username.of(value = username),
      password = Password.of(value = password)
    )
    userRepository.save(user = user)
  }
}
