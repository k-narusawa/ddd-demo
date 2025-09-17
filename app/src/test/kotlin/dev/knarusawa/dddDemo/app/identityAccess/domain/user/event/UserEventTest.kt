package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.AccountStatus
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.FamilyName
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.GivenName
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.LastLoginFailedAt
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.LoginFailureCount
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Password
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Username
import dev.knarusawa.dddDemo.util.ProtobufUtil
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("イベント_ユーザイベント")
class UserEventTest {
  @Nested
  @DisplayName("Prot obuf")
  inner class Protobuf {
    @Test
    @DisplayName("protobuf形式に変換できること")
    fun can_convert_to_protobuf() {
      val user = createUserInstance()
      val sut = SignupCompleted.of(user = user)

      val actual = sut.toEventMessage()

      assertEquals(sut.eventId.get(), actual.eventId)
      assertEquals(sut.userId.get(), actual.userId)
      assertEquals(sut.username.get(), actual.username)
      assertEquals(sut.emailVerified, actual.emailVerified)
      assertEquals(sut.givenName.get(), actual.givenName)
      assertEquals(sut.familyName.get(), actual.familyName)
      assertEquals(sut.loginFailureCount.get(), actual.loginFailureCount)
      assertEquals(
        sut.lastLoginFailedAt.get(),
        ProtobufUtil.toLocalDateTime(actual.lastLoginFailedAt),
      )
      assertEquals(
        sut.occurredAt,
        ProtobufUtil.toLocalDateTime(actual.occurredAt),
      )
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
      false,
      GivenName.of("名"),
      FamilyName.of("姓"),
      LoginFailureCount.init(),
      LastLoginFailedAt.init(),
      AccountStatus.NORMAL,
      1L,
      mutableListOf<UserEvent>(),
    )
  }
}
