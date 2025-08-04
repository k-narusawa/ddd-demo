package dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event

import dev.k_narusawa.ddd_demo.app.identity_access.domain.DomainEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User

data class UserSignupCompletedEvent(
  val user: User,
) : DomainEvent(source = user) {
  fun getEventName() = "ユーザのサインアップ完了イベント"
}
