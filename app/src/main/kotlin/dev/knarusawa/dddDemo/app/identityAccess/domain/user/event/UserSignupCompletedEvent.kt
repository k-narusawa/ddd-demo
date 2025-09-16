package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User

data class UserSignupCompletedEvent(
  val user: User,
) : IdentityAccessEvent(source = user)
