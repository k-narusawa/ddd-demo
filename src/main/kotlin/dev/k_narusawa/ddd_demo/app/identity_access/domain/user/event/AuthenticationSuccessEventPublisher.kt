package dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event

import dev.k_narusawa.ddd_demo.util.logger
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component

@Component
class AuthenticationSuccessEventPublisher : ApplicationEventPublisherAware {
  companion object {
    private var publisher: ApplicationEventPublisher? = null
    private val log = logger()

    fun publish(event: AuthenticationSuccessEvent) {
      log.info("AuthenticationSuccessEventの配信: ${event.user.userId.get()}")
      publisher!!.publishEvent(event)
      log.info("AuthenticationSuccessEventの配信完了: ${event.user.userId.get()}")
    }
  }

  override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
    publisher = applicationEventPublisher
  }
}
