package dev.knarusawa.dddDemo.app.project.application.eventHandler

import dev.knarusawa.dddDemo.app.project.adapter.gateway.message.TaskEventPublisher
import dev.knarusawa.dddDemo.app.project.application.eventHandler.event.OutboxEvent
import dev.knarusawa.dddDemo.app.project.application.port.OutboxEventInputBoundary
import dev.knarusawa.dddDemo.app.project.domain.outbox.OutboxRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(transactionManager = "projectTransactionManager")
class OutboxEventHandler(
  private val outboxRepository: OutboxRepository,
  private val taskEventPublisher: TaskEventPublisher,
) : OutboxEventInputBoundary {
  override fun handle(event: OutboxEvent) {
    val events = outboxRepository.findTop50ByProcessedAtIsNullOrderByOccurredAtAsc()
    events.forEach {
      taskEventPublisher.send(it.payload)
      it.process()
      outboxRepository.save(it)
    }
  }
}
