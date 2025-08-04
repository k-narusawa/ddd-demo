package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.registerUser

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username

data class SignupUserOutputData private constructor(
  val userId: String,
  val username: String
) {
  companion object {
    fun of(userId: UserId, username: Username): SignupUserOutputData {
      return SignupUserOutputData(
        userId = userId.get(),
        username = username.get()
      )
    }
  }
}
