package dev.k_narusawa.ddd_demo.app.identity_access.domain.activityLog

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginFailedDomainEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginSucceededDomainEvent
import dev.k_narusawa.ddd_demo.util.logger
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class ActivityLogEventListener(
  private val activityLogRepository: ActivityLogRepository
) {
  companion object {
    private val log = logger()
  }

  @EventListener
  @Async
  fun listen(event: LoginFailedDomainEvent) {
    log.info("${event}を受信")
    val log = ActivityLog.new(
      userId = event.user.userId,
      actionType = ActionType.LOGIN_FAILED,
      ipAddress = event.ipAddress,
      userAgent = event.userAgent,
      occurredOn = event.occurredOn
    )
    activityLogRepository.save(log = log)
  }

  @EventListener
  @Async
  fun listen(event: LoginSucceededDomainEvent) {
    log.info("${event}を受信")
    val log = ActivityLog.new(
      userId = event.user.userId,
      actionType = ActionType.LOGIN_SUCCESS,
      ipAddress = event.ipAddress,
      userAgent = event.userAgent,
      occurredOn = event.occurredOn
    )
    activityLogRepository.save(log = log)
  }
}
