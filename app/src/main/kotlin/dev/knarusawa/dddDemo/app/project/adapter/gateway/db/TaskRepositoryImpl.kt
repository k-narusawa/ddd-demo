package dev.knarusawa.dddDemo.app.project.adapter.gateway.db

import dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa.EventJpaEntity
import dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa.EventJpaRepository
import dev.knarusawa.dddDemo.app.project.domain.task.TaskId
import dev.knarusawa.dddDemo.app.project.domain.task.TaskRepository
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEvent
import org.springframework.stereotype.Repository

@Repository
class TaskRepositoryImpl(
  private val eventJpaRepository: EventJpaRepository,
) : TaskRepository {
  override fun save(event: TaskEvent) {
    val event = EventJpaEntity.of(event = event)
    eventJpaRepository.save(event = event)
  }

  override fun loadEvents(taskId: TaskId): List<TaskEvent> {
    val entities = eventJpaRepository.findByAggregateId(aggregateId = taskId.get())
    return entities.map {
      TaskEvent.fromPublishedLanguage(ba = it.eventData)
    }
  }
}
