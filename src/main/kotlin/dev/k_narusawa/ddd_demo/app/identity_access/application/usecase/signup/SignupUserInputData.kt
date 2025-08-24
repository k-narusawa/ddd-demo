package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.signup

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Password
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username

data class SignupUserInputData private constructor(
  val username: Username,
  val password: Password,
  val personalName: String,
) {
  companion object {
    fun of(username: String, password: String, personalName: String): SignupUserInputData {
      return SignupUserInputData(
        username = Username.of(value = username),
        password = Password.of(value = password),
        personalName = personalName
      )
    }
  }
}
