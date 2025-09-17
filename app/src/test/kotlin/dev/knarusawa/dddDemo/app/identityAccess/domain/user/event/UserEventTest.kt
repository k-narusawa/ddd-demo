package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.testFactory.TestUserFactory
import dev.knarusawa.dddDemo.util.ProtobufUtil
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
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
      val user = TestUserFactory.createUserInstance()
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
}
