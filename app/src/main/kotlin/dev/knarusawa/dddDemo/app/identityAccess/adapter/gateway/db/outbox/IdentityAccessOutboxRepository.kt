package dev.knarusawa.dddDemo.app.identityAccess.adapter.gateway.db.outbox

import dev.knarusawa.dddDemo.app.identityAccess.domain.EventId
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface IdentityAccessOutboxRepository : JpaRepository<Outbox, EventId> {
  fun save(outbox: Outbox)

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  fun findTop50ByProcessedAtIsNullOrderByOccurredAtAsc(): List<Outbox>
}
