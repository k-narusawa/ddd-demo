package dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event

import dev.k_narusawa.ddd_demo.app.identity_access.domain.DomainEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User

data class UsernameChangedEvent(
  val user: User,
  val userAgent: String,
  val ipAddress: String,
) : DomainEvent(source = user) {
  fun getEventName() = "Usernameの変更イベント"
}
