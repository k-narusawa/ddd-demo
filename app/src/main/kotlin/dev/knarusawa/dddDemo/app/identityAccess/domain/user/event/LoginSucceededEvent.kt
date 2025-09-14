package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User

data class LoginSucceededEvent(
  val user: User,
  val userAgent: String,
  val ipAddress: String,
) : IdentityAccessEvent(source = user)
