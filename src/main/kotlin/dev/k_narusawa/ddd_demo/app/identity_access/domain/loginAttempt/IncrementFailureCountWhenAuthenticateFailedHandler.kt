package dev.k_narusawa.ddd_demo.app.identity_access.domain.loginAttempt

import dev.k_narusawa.ddd_demo.app.identity_access.domain.exception.IdentityAccessDomainException
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginSucceededDomainEvent
import jakarta.transaction.Transactional
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Transactional(
  value = Transactional.TxType.REQUIRES_NEW,
  dontRollbackOn = [IdentityAccessDomainException::class]
)
class IncrementFailureCountWhenAuthenticateFailedHandler(
  private val loginAttemptRepository: LoginAttemptRepository
) {
  @EventListener
  fun handle(event: LoginSucceededDomainEvent) {
    val loginAttempt = loginAttemptRepository.findByUserId(event.user.userId)
      ?: LoginAttempt.new(userId = event.user.userId)
    loginAttempt.authenticateFailure()
    loginAttemptRepository.save(loginAttempt)
  }
}
