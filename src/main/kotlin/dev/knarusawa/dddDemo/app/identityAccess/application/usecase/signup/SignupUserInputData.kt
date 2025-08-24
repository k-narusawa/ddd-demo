package dev.knarusawa.dddDemo.app.identityAccess.application.usecase.signup

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Password
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Username

data class SignupUserInputData private constructor(
  val username: Username,
  val password: Password,
  val personalName: String,
) {
  companion object {
    fun of(
      username: String,
      password: String,
      personalName: String,
    ): SignupUserInputData =
      SignupUserInputData(
        username = Username.of(value = username),
        password = Password.of(value = password),
        personalName = personalName,
      )
  }
}
