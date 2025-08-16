package dev.k_narusawa.ddd_demo.app.identity_access.domain.exception

open class IdentityAccessDomainException(
  message: String? = "ドメイン例外",
  cause: Throwable? = null,
) : RuntimeException(message, cause)
