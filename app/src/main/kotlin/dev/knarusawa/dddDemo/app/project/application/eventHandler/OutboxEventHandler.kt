package dev.knarusawa.dddDemo.app.project.application.eventHandler

import dev.knarusawa.dddDemo.app.project.adapter.gateway.message.TaskEventPublisher
import dev.knarusawa.dddDemo.app.project.application.eventHandler.event.OutboxUpdatedEvent
import dev.knarusawa.dddDemo.app.project.application.port.OutboxEventInputBoundary
import dev.knarusawa.dddDemo.app.project.domain.outbox.OutboxRepository
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(transactionManager = "projectTransactionManager")
class OutboxEventHandler(
  private val outboxRepository: OutboxRepository,
  private val taskEventPublisher: TaskEventPublisher,
) : OutboxEventInputBoundary {
  override fun handle(event: OutboxUpdatedEvent) {
    val events = outboxRepository.findTop50ByProcessedAtIsNullOrderByOccurredAtAsc()
    events.forEach {
      val eventType = it.eventType
      val event = TaskEvent.deserialize(serialized = it.event)
      taskEventPublisher.send(message = event.toEventMessage().toByteArray())
      it.process()
      outboxRepository.save(it)
    }
  }
}
