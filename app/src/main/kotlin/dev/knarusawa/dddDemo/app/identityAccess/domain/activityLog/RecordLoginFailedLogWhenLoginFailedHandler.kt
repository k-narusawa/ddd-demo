package dev.knarusawa.dddDemo.app.identityAccess.domain.activityLog

import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.IdentityAccessDomainException
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.LoginFailedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(
  noRollbackFor = [IdentityAccessDomainException::class],
)
class RecordLoginFailedLogWhenLoginFailedHandler(
  private val activityLogRepository: ActivityLogRepository,
) {
  @EventListener
  fun handle(event: LoginFailedEvent) {
    val log =
      ActivityLog.new(
        userId = event.user.userId,
        actionType = ActionType.LOGIN_FAILED,
        ipAddress = event.ipAddress,
        userAgent = event.userAgent,
        occurredOn = event.occurredAt,
      )
    activityLogRepository.save(log = log)
  }
}
