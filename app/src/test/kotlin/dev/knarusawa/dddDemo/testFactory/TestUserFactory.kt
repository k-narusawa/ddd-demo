package dev.knarusawa.dddDemo.testFactory

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.AccountStatus
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.FamilyName
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.GivenName
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.LastLoginFailedAt
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.LoginFailureCount
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Password
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Username
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UserEvent
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

object TestUserFactory {
  fun createUserInstance(
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
