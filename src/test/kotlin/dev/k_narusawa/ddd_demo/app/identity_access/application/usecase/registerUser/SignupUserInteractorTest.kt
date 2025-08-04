package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.registerUser

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Password
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserRepository
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserService
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username
import dev.k_narusawa.ddd_demo.app.identity_access.exception.SignupException
import dev.k_narusawa.ddd_demo.executionListener.DatabaseCleanupListener
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners

@SpringBootTest
@DisplayName("ユースケース_サインアップ")
@TestExecutionListeners(listeners = [DatabaseCleanupListener::class])
class SignupUserInteractorTest @Autowired constructor(
  private val userService: UserService,
  private val userRepository: UserRepository,
  private val signupUserInteractor: SignupUserInteractor
) {

  @Nested
  @DisplayName("ユーザー登録")
  inner class Signup {
    @Test
    @DisplayName("ユーザー登録が成功する")
    fun signup_succeeds() = runBlocking {
      val username = Username.of("testuser")
      val input = SignupUserInputData.of(username.get(), "!Password0")

      val sut = signupUserInteractor.handle(input)

      assertNotNull(sut)
      assertEquals(username.get(), sut.username)
    }

    @Test
    @DisplayName("既に存在するユーザー名を登録しようとすると例外がスローされる")
    fun signup_with_existing_username_throws_exception() {
      val username = Username.of("testuser")
      createUser(username = "testuser", password = "!Password0")
      val input = SignupUserInputData.of(username.get(), "!Password0")

      assertThrows(SignupException::class.java) {
        runBlocking {
          signupUserInteractor.handle(input)
        }
      }
    }

    private fun createUser(username: String, password: String) {
      val user = User.signup(
        username = Username.of(value = username),
        password = Password.of(value = password)
      )
      userRepository.save(user = user)
    }
  }
}
