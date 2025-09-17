package dev.knarusawa.dddDemo.app.identityAccess.domain.activityLog

import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.IdentityAccessDomainException
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.LoginSucceeded
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(
  noRollbackFor = [IdentityAccessDomainException::class],
)
class RecordLoginSucceededLogWhenLoginSucceededHandler(
  private val activityLogRepository: ActivityLogRepository,
) {
  @EventListener
  @Async
  fun handle(event: LoginSucceeded) {
    val log =
      ActivityLog.new(
        userId = event.userId,
        actionType = ActionType.LOGIN_SUCCESS,
        ipAddress = event.ipAddress,
        userAgent = event.userAgent,
        occurredOn = event.occurredAt,
      )
    activityLogRepository.save(log = log)
  }
}
