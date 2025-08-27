package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User

data class UsernameChangedEvent(
  val user: User,
  val userAgent: String,
  val ipAddress: String,
) : IdentityAccessEvent(source = user) {
  fun getEventName() = "Usernameの変更イベント"
}
