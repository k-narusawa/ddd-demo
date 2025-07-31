package dev.k_narusawa.ddd_demo.app.identity_access.domain.loginAttempt

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class LoginAttemptTest {
  @Nested
  @DisplayName("インスタンス化")
  inner class Instantiation {
    @Test
    @DisplayName("LoginAttemptが正しく作成される")
    fun `test new`() {
      val userId = UserId.new()
      val attempt = LoginAttempt.new(userId)

      assertEquals(userId, attempt.userId, "LoginAttemptのuserIdが正しく設定されているべき")
    }

    @Nested
    @DisplayName("等価性")
    inner class Equality {
      @Test
      @DisplayName("同じIDを持つAttemptは等価である")
      fun `test equals and hashCode with same ID`() {
        val userId = UserId.new()
        val attempt1 = LoginAttempt.new(userId)
        val attempt2 = LoginAttempt.new(userId)

        assertEquals(attempt1, attempt2, "同じIDを持つAttemptは等価であるべき")
        assertEquals(
          attempt1.hashCode(),
          attempt2.hashCode(),
          "同じIDを持つAttemptのhashCodeは同じであるべき"
        )
      }

      @Test
      @DisplayName("異なるIDを持つAttemptは等価ではない")
      fun `test equals with different IDs`() {
        val user1 = LoginAttempt.new(UserId.new())
        val user2 = LoginAttempt.new(UserId.new())

        assertNotEquals(user1, user2, "異なるIDを持つAttemptは等価ではないべき")
      }
    }
  }

  @Nested
  @DisplayName("失敗回数のカウント")
  inner class FailureCount {
    @Test
    @DisplayName("LoginAttemptの失敗回数が正しく初期化される")
    fun `test initial failure count`() {
      val userId = UserId.new()
      val attempt = LoginAttempt.new(userId)

      assertEquals(0, attempt.getFailureCount(), "LoginAttemptの初期失敗回数は0であるべき")
      attempt.authenticateFailure()
      assertEquals(1, attempt.getFailureCount(), "LoginAttemptの失敗回数が1に増加するべき")
    }

    @Test
    @DisplayName("LOGIN_ATTEMPT_LIMITの指定回数失敗したらロックされること")
    fun `test account lock`() {
      val userId = UserId.new()
      val attempt = LoginAttempt.new(userId)

      assertEquals(false, attempt.isLocked(), "アカウントがロックされるべきでない")
      attempt.authenticateFailure()
      attempt.authenticateFailure()
      attempt.authenticateFailure()
      attempt.authenticateFailure()
      attempt.authenticateFailure()
      assertEquals(5, attempt.getFailureCount(), "LoginAttemptの失敗回数が5に増加するべき")
      assertEquals(true, attempt.isLocked(), "LoginAttemptの失敗回数が5に増加するべき")
    }
  }
}