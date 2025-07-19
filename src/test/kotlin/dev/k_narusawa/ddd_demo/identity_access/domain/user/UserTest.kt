package dev.k_narusawa.ddd_demo.identity_access.domain.user

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Password
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

class UserTest {
  @Test
  @DisplayName("同じIDを持つUserは等価である")
  fun `test equals and hashCode with same ID`() {
    // Arrange
    val userId = UserId.new()
    val user1 = createUserInstance(userId, "Taro")
    val user2 = createUserInstance(userId, "Jiro") // 名前が違う

    // Assert
    assertEquals(user1, user2, "同じIDを持つUserは等価であるべき")
    assertEquals(user1.hashCode(), user2.hashCode(), "同じIDを持つUserのhashCodeは同じであるべき")
  }

  @Test
  @DisplayName("異なるIDを持つUserは等価ではない")
  fun `test equals with different IDs`() {
    // Arrange
    val user1 = createUserInstance(UserId.new(), "Taro")
    val user2 = createUserInstance(UserId.new(), "Taro") // 名前は同じ

    // Assert
    assertNotEquals(user1, user2, "異なるIDを持つUserは等価ではないべき")
  }

  @Test
  @DisplayName("ユーザー名を変更できる")
  fun `test changeUsername`() {
    // Arrange
    val user = createUserInstance(UserId.new(), "Taro")
    val newUsername = Username.of("Jiro")

    // Act
    user.changeUsername(newUsername)

    // Assert
    assertEquals(newUsername, user.username, "ユーザー名が正しく変更されているべき")
  }

  /**
   * リフレクションを使用してUserのインスタンスを生成するヘルパー関数
   */
  private fun createUserInstance(id: UserId, name: String): User {
    val constructor = User::class.primaryConstructor!!
    constructor.isAccessible = true
    return constructor.call(id, Username.of(name), Password.of("!Password0"))
  }
}
