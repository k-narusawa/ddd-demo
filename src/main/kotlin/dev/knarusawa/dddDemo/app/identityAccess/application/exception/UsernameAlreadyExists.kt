package dev.knarusawa.dddDemo.app.identityAccess.application.exception

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Username

class UsernameAlreadyExists(
  message: String? = "Usernameが登録ずみ",
  cause: Throwable? = null,
  val username: Username,
) : IdentityAccessApplicationException(message, cause)
