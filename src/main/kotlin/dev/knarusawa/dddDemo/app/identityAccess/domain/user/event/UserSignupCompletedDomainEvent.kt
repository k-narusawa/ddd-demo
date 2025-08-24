package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessDomainEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User

data class UserSignupCompletedDomainEvent(
  val user: User,
  val personalName: String,
) : IdentityAccessDomainEvent(source = user)
