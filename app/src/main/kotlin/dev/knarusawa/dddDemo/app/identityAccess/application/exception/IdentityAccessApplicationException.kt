package dev.knarusawa.dddDemo.app.identityAccess.application.exception

sealed class IdentityAccessApplicationException(
  message: String? = "アプリケーション例外",
  cause: Throwable? = null,
) : RuntimeException(message, cause)
