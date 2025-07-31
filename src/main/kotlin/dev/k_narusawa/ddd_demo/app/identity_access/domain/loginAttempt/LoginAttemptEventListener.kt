package dev.k_narusawa.ddd_demo.app.identity_access.domain.loginAttempt

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginFailedEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginSucceededEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class LoginAttemptEventListener(
  private val loginAttemptRepository: LoginAttemptRepository
) {
  @EventListener
  @Async
  fun on(event: LoginSucceededEvent) {

  }

  @EventListener
  @Async
  fun on(event: LoginFailedEvent) {

  }
}