package dev.k_narusawa.ddd_demo.app.identity_access.domain.exception

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId

class LoginFailed(
  message: String? = "ログインに失敗",
  cause: Throwable? = null,
  val userId: UserId? = null,
) : IdentityAccessDomainException(message, cause)
