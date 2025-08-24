package dev.k_narusawa.ddd_demo.app.identity_access.domain.loginAttempt

import dev.k_narusawa.ddd_demo.app.identity_access.domain.exception.IdentityAccessDomainException
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginFailedDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(
    noRollbackFor = [IdentityAccessDomainException::class],
    propagation = Propagation.REQUIRES_NEW,
)
class IncrementFailureCountWhenAuthenticateFailedHandler(
    private val loginAttemptRepository: LoginAttemptRepository,
) {
    @EventListener
    fun handle(event: LoginFailedDomainEvent) {
        val loginAttempt =
            loginAttemptRepository.findByUserId(event.user.userId)
                ?: LoginAttempt.new(userId = event.user.userId)
        loginAttempt.authenticateFailure()
        loginAttemptRepository.save(loginAttempt)
    }
}
