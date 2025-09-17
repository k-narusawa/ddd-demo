package dev.knarusawa.dddDemo.app.project.adapter.service

import dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa.AggregateType
import dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa.EventJpaEntity
import dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa.EventJpaRepository
import dev.knarusawa.dddDemo.app.project.adapter.gateway.message.TaskEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class EventPublishWhenEventListenedHandler(
  private val eventJpaRepository: EventJpaRepository,
  private val taskEventPublisher: TaskEventPublisher,
) {
  fun handle(event: EventJpaEntity) {
    when (event.aggregateType) {
      AggregateType.MEMBER -> {
        TODO()
      }

      AggregateType.PROJECT -> {
        TODO()
      }

      AggregateType.TASK -> {
        taskEventPublisher.publish(message = event.eventData)
      }
    }
    event.published()
    eventJpaRepository.save(event)
  }
}
