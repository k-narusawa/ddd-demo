package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.registerUser

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Password
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username

data class RegisterUserInputData private constructor(
  val username: Username,
  val password: Password
) {
  companion object {
    fun of(username: String, password: String): RegisterUserInputData {
      return RegisterUserInputData(
        username = Username.of(value = username),
        password = Password.of(value = password)
      )
    }
  }
}