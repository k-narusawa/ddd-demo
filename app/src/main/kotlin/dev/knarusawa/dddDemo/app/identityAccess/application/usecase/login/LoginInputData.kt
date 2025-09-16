package dev.knarusawa.dddDemo.app.identityAccess.application.usecase.login

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Username

data class LoginInputData(
  val username: Username,
  val password: String,
  val userAgent: String,
  val ipAddress: String,
) {
  companion object {
    fun of(
      username: String,
      password: String,
      userAgent: String,
      remoteAddr: String,
    ): LoginInputData =
      LoginInputData(
        username = Username.of(value = username),
        password = password,
        userAgent = userAgent,
        ipAddress = remoteAddr,
      )
  }
}
