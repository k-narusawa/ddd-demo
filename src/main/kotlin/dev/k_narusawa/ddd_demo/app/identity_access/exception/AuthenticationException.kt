package dev.k_narusawa.ddd_demo.app.identity_access.exception

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId

class AuthenticationException(
  message: String,
  cause: Throwable? = null,
  val userId: UserId? = null,
  val isLock: Boolean? = null
) : RuntimeException(message, cause)