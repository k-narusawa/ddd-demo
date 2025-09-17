package dev.knarusawa.dddDemo.app.project.adapter.gateway.db

import dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa.EventJpaEntity
import dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa.EventJpaRepository
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectRepository
import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectEvent
import org.springframework.stereotype.Repository

@Repository
class ProjectRepositoryImpl(
  private val eventJpaRepository: EventJpaRepository,
) : ProjectRepository {
  override fun save(event: ProjectEvent) {
    val event = EventJpaEntity.of(event = event)
    eventJpaRepository.save(event = event)
  }

  override fun loadEvents(projectId: ProjectId): List<ProjectEvent> {
    val entities = eventJpaRepository.findByAggregateId(aggregateId = projectId.get())
    return entities.map {
      ProjectEvent.from(ba = it.eventData)
    }
  }
}
