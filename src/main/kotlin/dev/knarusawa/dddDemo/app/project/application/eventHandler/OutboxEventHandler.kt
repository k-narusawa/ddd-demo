package dev.knarusawa.dddDemo.app.project.application.eventHandler

import dev.knarusawa.dddDemo.app.project.adapter.gateway.message.TaskChangedPublisher
import dev.knarusawa.dddDemo.app.project.adapter.gateway.message.TaskCompletedPublisher
import dev.knarusawa.dddDemo.app.project.adapter.gateway.message.TaskCreatedPublisher
import dev.knarusawa.dddDemo.app.project.application.eventHandler.event.OutboxEvent
import dev.knarusawa.dddDemo.app.project.application.port.OutboxEventInputBoundary
import dev.knarusawa.dddDemo.app.project.domain.outbox.EventType
import dev.knarusawa.dddDemo.app.project.domain.outbox.OutboxEventRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class OutboxEventHandler(
  private val outboxEventRepository: OutboxEventRepository,
  private val taskCreatedPublisher: TaskCreatedPublisher,
  private val taskChangedPublisher: TaskChangedPublisher,
  private val taskCompletedPublisher: TaskCompletedPublisher,
) : OutboxEventInputBoundary {
  override fun handle(event: OutboxEvent) {
    val events = outboxEventRepository.findTop50ByProcessedAtIsNullOrderByOccurredAtAsc()
    events.forEach {
      when (it.eventType) {
        EventType.TASK_CREATED -> taskCreatedPublisher.send(it.payload)
        EventType.TASK_CHANGED -> taskChangedPublisher.send(it.payload)
        EventType.TASK_COMPLETED -> taskCompletedPublisher.send(it.payload)
      }
      it.process()
      outboxEventRepository.save(it)
    }
  }
}
