package dev.knarusawa.dddDemo.app.identityAccess.domain.exception

class TokenUnauthorized(
  message: String? = "トークンの検証に失敗しました",
  cause: Throwable? = null,
) : IdentityAccessDomainException(message, cause)
