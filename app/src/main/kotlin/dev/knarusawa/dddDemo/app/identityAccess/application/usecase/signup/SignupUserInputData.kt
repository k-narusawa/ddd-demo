package dev.knarusawa.dddDemo.app.identityAccess.application.usecase.signup

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.FamilyName
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.GivenName
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Password
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Username

data class SignupUserInputData(
  val username: Username,
  val password: Password,
  val givenName: GivenName,
  val familyName: FamilyName,
) {
  companion object {
    fun of(
      username: String,
      password: String,
      givenName: String,
      familyName: String,
    ): SignupUserInputData =
      SignupUserInputData(
        username = Username.of(value = username),
        password = Password.of(value = password),
        givenName = GivenName.of(value = givenName),
        familyName = FamilyName.of(value = familyName),
      )
  }
}
