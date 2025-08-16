package dev.k_narusawa.ddd_demo.app.identity_access.application.exception

sealed class IdentityAccessApplicationException(
  message: String? = "アプリケーション例外",
  cause: Throwable? = null,
) : RuntimeException(message, cause)
