package dev.knarusawa.dddDemo.app.identityAccess.domain.exception

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId

class AccountLock(
  message: String? = "アカウントがロックされています",
  cause: Throwable? = null,
  val userId: UserId? = null,
) : IdentityAccessDomainException(message, cause)
