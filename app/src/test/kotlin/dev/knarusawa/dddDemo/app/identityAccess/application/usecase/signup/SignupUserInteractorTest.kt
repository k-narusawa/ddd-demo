package dev.knarusawa.dddDemo.app.identityAccess.application.usecase.signup

import dev.knarusawa.dddDemo.app.identityAccess.application.exception.UsernameAlreadyExists
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.FamilyName
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.GivenName
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Password
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserRepository
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Username
import dev.knarusawa.dddDemo.app.project.domain.member.MemberRepository
import dev.knarusawa.dddDemo.executionListener.DatabaseCleanupListener
import dev.knarusawa.dddDemo.testFactory.TestUserIntegrationFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners

@SpringBootTest
@DisplayName("ユースケース_サインアップ")
@TestExecutionListeners(listeners = [DatabaseCleanupListener::class])
class SignupUserInteractorTest
  @Autowired
  constructor(
    private val userRepository: UserRepository,
    private val sut: SignupUserInteractor,
    private val memberRepository: MemberRepository,
    private val testUserIntegrationFactory: TestUserIntegrationFactory,
  ) {
    @Test
    @DisplayName("ユーザー登録が成功する")
    fun signup_succeeds() {
      val username = Username.of("test@example.com")
      val givenName = GivenName.of(value = "名")
      val familyName = FamilyName.of(value = "姓")
      val input =
        SignupUserInputData.of(username.get(), "!Password0", givenName.get(), familyName.get())

      val sut = sut.handle(input)

      val user = userRepository.findByUsername(username = Username.of(sut.username))
      assertEquals(sut.userId, user?.userId?.get())
      assertEquals(username.get(), user?.getUsername()?.get())
    }

    @Test
    @DisplayName("既に存在するユーザー名を登録しようとすると例外がスローされる")
    fun signup_with_existing_username_throws_exception() {
      val username = Username.of("test@example.com")
      val givenName = GivenName.of("名")
      val familyName = FamilyName.of("姓")
      testUserIntegrationFactory.createUser(
        username = Username.of("test@example.com"),
        password = Password.of("!Password0"),
        givenName = givenName,
        familyName = familyName,
      )
      val input =
        SignupUserInputData.of(username.get(), "!Password0", givenName.get(), familyName.get())

      assertThrows(UsernameAlreadyExists::class.java) {
        sut.handle(input)
      }
    }
  }
