package dev.k_narusawa.ddd_demo.app.identity_access.domain.activityLog

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginFailedEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginSucceededEvent
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
  fun listen(event: LoginFailedEvent) {
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
  fun listen(event: LoginSucceededEvent) {
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
