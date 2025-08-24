package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessDomainEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User

data class LoginSucceededDomainEvent(
  val user: User,
  val userAgent: String,
  val ipAddress: String,
) : IdentityAccessDomainEvent(source = user)
