package dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventJpaRepository : JpaRepository<EventJpaEntity, String> {
  fun save(event: EventJpaEntity)

  fun findByAggregateId(aggregateId: String): List<EventJpaEntity>
}
