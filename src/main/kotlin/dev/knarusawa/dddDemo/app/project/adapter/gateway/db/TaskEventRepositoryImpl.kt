package dev.knarusawa.dddDemo.app.project.adapter.gateway.db

import dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa.EventJpaEntity
import dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa.EventJpaRepository
import dev.knarusawa.dddDemo.app.project.domain.task.TaskId
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEvent
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEventRepository
import org.springframework.stereotype.Repository

@Repository
class TaskEventRepositoryImpl(
  private val eventJpaRepository: EventJpaRepository,
) : TaskEventRepository {
  override fun save(taskEvent: TaskEvent) {
    val event = EventJpaEntity.of(event = taskEvent)
    eventJpaRepository.save(event = event)
  }

  override fun findByTaskIdOrderByOccurredAtAsc(taskId: TaskId): List<TaskEvent> {
    val entities = eventJpaRepository.findByAggregateId(aggregateId = taskId.get())
    return entities.map {
      TaskEvent.fromPayload(payload = it.eventData)
    }
  }
}
