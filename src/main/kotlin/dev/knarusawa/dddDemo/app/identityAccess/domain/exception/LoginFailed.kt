package dev.knarusawa.dddDemo.app.identityAccess.domain.exception

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId

class LoginFailed(
  message: String? = "ログインに失敗",
  cause: Throwable? = null,
  val userId: UserId? = null,
) : IdentityAccessDomainException(message, cause)
