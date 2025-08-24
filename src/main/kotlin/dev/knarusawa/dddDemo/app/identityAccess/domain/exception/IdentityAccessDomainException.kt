package dev.knarusawa.dddDemo.app.identityAccess.domain.exception

sealed class IdentityAccessDomainException(
  message: String? = "ドメイン例外",
  cause: Throwable? = null,
) : RuntimeException(message, cause)
