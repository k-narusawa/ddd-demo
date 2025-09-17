package dev.knarusawa.dddDemo.app.project.application.eventHandler

import dev.knarusawa.dddDemo.app.project.adapter.gateway.message.TaskEventPublisher
import dev.knarusawa.dddDemo.app.project.application.eventHandler.event.OutboxUpdatedEvent
import dev.knarusawa.dddDemo.app.project.application.port.OutboxEventInputBoundary
import dev.knarusawa.dddDemo.app.project.domain.outbox.ProjectOutboxRepository
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(transactionManager = "projectTransactionManager")
class OutboxEventHandler(
  private val projectOutboxRepository: ProjectOutboxRepository,
  private val taskEventPublisher: TaskEventPublisher,
) : OutboxEventInputBoundary {
  override fun handle(event: OutboxUpdatedEvent) {
    val events = projectOutboxRepository.findTop50ByProcessedAtIsNullOrderByOccurredAtAsc()
    events.forEach {
      val event = TaskEvent.deserialize(serialized = it.event)
      taskEventPublisher.send(message = event.toEventMessage().toByteArray())
      it.process()
      projectOutboxRepository.save(it)
    }
  }
}
