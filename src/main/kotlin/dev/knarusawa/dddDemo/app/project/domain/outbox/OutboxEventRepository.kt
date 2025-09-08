package dev.knarusawa.dddDemo.app.project.domain.outbox

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository

@Repository
interface OutboxEventRepository : JpaRepository<OutboxEvent, EventId> {
  fun save(event: OutboxEvent)

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  fun findTop50ByProcessedAtIsNullOrderByOccurredAtAsc(): List<OutboxEvent>
}
