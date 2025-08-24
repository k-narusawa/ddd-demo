package dev.k_narusawa.ddd_demo.app.identity_access.domain.exception

class TokenUnauthorized(
    message: String? = "トークンの検証に失敗しました",
    cause: Throwable? = null,
) : IdentityAccessDomainException(message, cause)
