package dev.k_narusawa.ddd_demo.app.identity_access.domain.activityLog

import dev.k_narusawa.ddd_demo.app.identity_access.domain.exception.IdentityAccessDomainException
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginFailedDomainEvent
import jakarta.transaction.Transactional
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
@Transactional(
  value = Transactional.TxType.REQUIRES_NEW,
  dontRollbackOn = [IdentityAccessDomainException::class]
)
class RecordLoginFailedLogWhenLoginFailedHandler(
  private val activityLogRepository: ActivityLogRepository
) {
  @EventListener
  @Async
  fun listen(event: LoginFailedDomainEvent) {
    val log = ActivityLog.new(
      userId = event.user.userId,
      actionType = ActionType.LOGIN_FAILED,
      ipAddress = event.ipAddress,
      userAgent = event.userAgent,
      occurredOn = event.occurredAt
    )
    activityLogRepository.save(log = log)
  }
}
