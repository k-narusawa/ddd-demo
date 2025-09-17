package dev.knarusawa.dddDemo.app.identityAccess.application

import dev.knarusawa.dddDemo.app.identityAccess.domain.DomainEvent

interface DomainEventPublisher {
  fun publish(event: DomainEvent<*>)
}
