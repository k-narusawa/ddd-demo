package dev.knarusawa.dddDemo.app.identityAccess.domain.loginAttempt

import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.IdentityAccessDomainException
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.LoginSucceededDomainEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
@Transactional(
  noRollbackFor = [IdentityAccessDomainException::class],
)
class ResetFailureCountWhenAuthenticateSucceededHandler(
  private val loginAttemptRepository: LoginAttemptRepository,
) {
  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  fun handle(event: LoginSucceededDomainEvent) {
    val loginAttempt =
      loginAttemptRepository.findByUserId(event.user.userId)
        ?: return
    loginAttempt.authenticateSuccess()
    loginAttemptRepository.save(loginAttempt)
  }
}
