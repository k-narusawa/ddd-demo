package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.signup

import dev.k_narusawa.ddd_demo.app.identity_access.application.exception.UsernameAlreadyExists
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Password
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserRepository
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username
import dev.k_narusawa.ddd_demo.app.task.domain.actor.ActorId
import dev.k_narusawa.ddd_demo.app.task.domain.actor.ActorRepository
import dev.k_narusawa.ddd_demo.executionListener.DatabaseCleanupListener
import dev.k_narusawa.ddd_demo.testFactory.TestUserFactory
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
  private val userRepository: UserRepository,
  private val sut: SignupUserInteractor,

  private val actorRepository: ActorRepository,
  private val testUserFactory: TestUserFactory
) {

  @Nested
  @DisplayName("ユーザー登録")
  inner class Signup {
    @Test
    @DisplayName("ユーザー登録が成功する")
    fun signup_succeeds() = runBlocking {
      val username = Username.of("test@example.com")
      val input = SignupUserInputData.of(username.get(), "!Password0", "テスト氏名")

      val sut = sut.handle(input)

      assertNotNull(sut)

      val user = userRepository.findByUsername(username = Username.of(sut.username))
      assertEquals(sut.userId, user?.userId?.get())
      assertEquals(username.get(), user?.getUsername()?.get())
      val actor = actorRepository.findByActorId(actorId = ActorId.from(value = sut.userId))
      assertNotNull(actor)
      assertEquals(input.personalName, actor?.getPersonalName()?.get())
    }

    @Test
    @DisplayName("既に存在するユーザー名を登録しようとすると例外がスローされる")
    fun signup_with_existing_username_throws_exception() {
      val username = Username.of("test@example.com")
      testUserFactory.createUser(
        username = Username.of("test@example.com"),
        password = Password.of("!Password0"),
        personalName = "テスト氏名"
      )
      val input = SignupUserInputData.of(username.get(), "!Password0", "テスト氏名")

      assertThrows(UsernameAlreadyExists::class.java) {
        runBlocking {
          sut.handle(input)
        }
      }
    }
  }
}
