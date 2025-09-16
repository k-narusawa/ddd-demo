package dev.knarusawa.dddDemo.app.project.domain.outbox

import dev.knarusawa.dddDemo.app.project.domain.EventId
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository

@Repository
interface OutboxRepository : JpaRepository<Outbox, EventId> {
  fun save(outbox: Outbox)

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  fun findTop50ByProcessedAtIsNullOrderByOccurredAtAsc(): List<Outbox>
}
