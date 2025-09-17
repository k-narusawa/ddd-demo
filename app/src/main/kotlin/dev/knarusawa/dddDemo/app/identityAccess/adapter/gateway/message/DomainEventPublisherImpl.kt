package dev.knarusawa.dddDemo.app.identityAccess.adapter.gateway.message

import dev.knarusawa.dddDemo.app.identityAccess.application.DomainEventPublisher
import dev.knarusawa.dddDemo.app.identityAccess.domain.DomainEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.outbox.IdentityAccessOutboxRepository
import dev.knarusawa.dddDemo.app.identityAccess.domain.outbox.Outbox
import org.springframework.stereotype.Component

@Component
class DomainEventPublisherImpl(
  private val identityAccessOutboxRepository: IdentityAccessOutboxRepository,
) : DomainEventPublisher {
  override fun publish(event: DomainEvent<*>) {
    val outbox = Outbox.of(event = event)
    identityAccessOutboxRepository.save(outbox = outbox)
  }
}
