package dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User
import org.springframework.context.ApplicationEvent
import java.time.LocalDateTime

data class ChangeUsernameEvent(
  val user: User,
  val userAgent: String,
  val ipAddress: String,
  val occurredOn: LocalDateTime = LocalDateTime.now()
) : ApplicationEvent(user) {
  fun getEventName() = "Usernameの変更イベント"
}
