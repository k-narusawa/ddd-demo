package dev.k_narusawa.ddd_demo.app.identity_access.application.exception

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Username

class UsernameAlreadyExists(
  message: String? = "Usernameが登録ずみ",
  cause: Throwable? = null,
  val username: Username
) : IdentityAccessApplicationException(message, cause)
