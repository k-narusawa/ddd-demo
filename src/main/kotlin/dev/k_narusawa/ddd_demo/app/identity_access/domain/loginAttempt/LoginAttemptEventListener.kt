package dev.k_narusawa.ddd_demo.app.identity_access.domain.loginAttempt

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.AuthenticationFailedEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.AuthenticationSuccessEvent
import dev.k_narusawa.ddd_demo.util.logger
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class LoginAttemptEventListener(
  private val loginAttemptRepository: LoginAttemptRepository
) {
  companion object {
    private val log = logger()
  }

  @EventListener
//  @Async ここのイベントは同期で処理をする必要がある
  fun listen(event: AuthenticationFailedEvent) {
    log.info("AuthenticationFailedEventを受信")
    val attempt = loginAttemptRepository.findByUserId(userId = event.user.userId)
      ?: LoginAttempt.new(userId = event.user.userId)
    attempt.authenticateFailure()
    loginAttemptRepository.save(attempt = attempt)
  }

  @EventListener
//  @Async ここのイベントは同期で処理をする必要がある
  fun listen(event: AuthenticationSuccessEvent) {
    log.info("AuthenticationSuccessEventを受信")
    val attempt = loginAttemptRepository.findByUserId(userId = event.user.userId)
      ?: LoginAttempt.new(userId = event.user.userId)
    attempt.authenticateSuccess()
    loginAttemptRepository.save(attempt = attempt)
  }
}