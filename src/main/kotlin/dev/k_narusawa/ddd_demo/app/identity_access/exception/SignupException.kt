package dev.k_narusawa.ddd_demo.app.identity_access.exception

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username

class SignupException(
  message: String,
  cause: Throwable? = null,
  val username: Username
) : RuntimeException(message, cause)
