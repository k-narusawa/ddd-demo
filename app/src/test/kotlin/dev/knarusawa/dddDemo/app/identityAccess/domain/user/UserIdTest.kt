package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.UUID

@DisplayName("ドメイン_値オブジェクト_ユーザID")
class UserIdTest {
  @Nested
  @DisplayName("インスタンス化")
  inner class Instantiation {
    @Test
    @DisplayName("コンストラクタでインスタンスを生成できる")
    fun `can instantiate with constructor`() {
      val uuid = UUID.randomUUID().toString()

      val sut = UserId(uuid)

      assertEquals(uuid, sut.get())
    }

    @Test
    @DisplayName("new メソッドでインスタンスを生成できる")
    fun `can instantiate with new method`() {
      val sut = UserId.new()

      assertNotNull(sut)
      assertDoesNotThrow { UUID.fromString(sut.get()) }
    }
  }

  @Nested
  @DisplayName("等価性")
  inner class Equality {
    @Test
    @DisplayName("同じ値を持つインスタンスは等価である")
    fun `instances with same value are equal`() {
      val uuid = UUID.randomUUID().toString()
      val id1 = UserId(uuid)
      val id2 = UserId(uuid)

      assertEquals(id1, id2)
      assertEquals(id1.hashCode(), id2.hashCode())
    }

    @Test
    @DisplayName("異なる値を持つインスタンスは等価ではない")
    fun `instances with different values are not equal`() {
      val id1 = UserId.new()
      val id2 = UserId.new()

      assertNotEquals(id1, id2)
    }
  }
}
