package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.login

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Password
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username

data class LoginInputData(
  val username: Username,
  val password: String
) {
  companion object {
    fun of(
      username: String,
      password: String
    ): LoginInputData {
      return LoginInputData(
        username = Username.of(value = username),
        password = password
      )
    }
  }
}
