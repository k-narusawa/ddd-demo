package dev.knarusawa.dddDemo.app.identityAccess.application.usecase.signup

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Username

data class SignupUserOutputData private constructor(
  val userId: String,
  val username: String,
) {
  companion object {
    fun of(
      userId: UserId,
      username: Username,
    ): SignupUserOutputData =
      SignupUserOutputData(
        userId = userId.get(),
        username = username.get(),
      )
  }
}
