package dev.knarusawa.dddDemo.app.identityAccess.adapter.service

import dev.knarusawa.dddDemo.app.identityAccess.adapter.gateway.message.UserEventPublisher
import dev.knarusawa.dddDemo.app.identityAccess.domain.outbox.IdentityAccessOutboxRepository
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UserEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UserEventType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class OutboxSubscribeEventHandler(
  private val outboxRepository: IdentityAccessOutboxRepository,
  private val userEventPublisher: UserEventPublisher,
) {
  fun handle() {
    val outboxContents = outboxRepository.findTop50ByProcessedAtIsNullOrderByOccurredAtAsc()
    outboxContents.forEach {
      when {
        UserEventType.isUserEventType(it.eventType) -> {
          val event = UserEvent.deserialize(serialized = it.event)
          userEventPublisher.send(event.toEventMessage().toByteArray())
        }

        else -> throw IllegalStateException("予期せぬイベント: ${it.eventType}")
      }
      it.process()
      outboxRepository.save(it)
    }
  }
}
