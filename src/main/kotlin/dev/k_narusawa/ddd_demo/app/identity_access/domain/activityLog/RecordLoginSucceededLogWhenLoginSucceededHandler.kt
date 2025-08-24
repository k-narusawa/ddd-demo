package dev.k_narusawa.ddd_demo.app.identity_access.domain.activityLog

import dev.k_narusawa.ddd_demo.app.identity_access.domain.exception.IdentityAccessDomainException
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginSucceededDomainEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
@Transactional(
  noRollbackFor = [IdentityAccessDomainException::class]
)
class RecordLoginSucceededLogWhenLoginSucceededHandler(
  private val activityLogRepository: ActivityLogRepository
) {
  @EventListener
  @Async
  fun handle(event: LoginSucceededDomainEvent) {
    val log = ActivityLog.new(
      userId = event.user.userId,
      actionType = ActionType.LOGIN_SUCCESS,
      ipAddress = event.ipAddress,
      userAgent = event.userAgent,
      occurredOn = event.occurredAt
    )
    activityLogRepository.save(log = log)
  }
}
