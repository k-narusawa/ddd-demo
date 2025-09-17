package dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository

@Repository
interface EventJpaRepository : JpaRepository<EventJpaEntity, String> {
  fun save(event: EventJpaEntity)

  fun findByAggregateId(aggregateId: String): List<EventJpaEntity>

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  fun findTop50ByPublishedAtIsNullOrderByOccurredAtAsc(): List<EventJpaEntity>
}
