package dev.knarusawa.dddDemo.app.identityAccess.adapter.service

import dev.knarusawa.dddDemo.app.identityAccess.adapter.gateway.db.outbox.IdentityAccessOutboxRepository
import dev.knarusawa.dddDemo.app.identityAccess.adapter.gateway.db.outbox.Outbox
import dev.knarusawa.dddDemo.app.identityAccess.adapter.gateway.message.UserEventPublisher
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UserEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UserEventType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class EventPublishWhenOutboxEventListenedHandler(
  private val outboxRepository: IdentityAccessOutboxRepository,
  private val userEventPublisher: UserEventPublisher,
) {
  fun handle(outbox: Outbox) {
    when {
      UserEventType.isUserEventType(outbox.eventType) -> {
        val event = UserEvent.deserialize(serialized = outbox.event)
        userEventPublisher.publish(event.toPublishedLanguage().toByteArray())
      }

      else -> throw IllegalStateException("予期せぬイベント: ${outbox.eventType}")
    }
    outbox.process()
    outboxRepository.save(outbox)
  }
}
