package dev.knarusawa.dddDemo.app.task.domain.outbox

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OutboxEventRepository : JpaRepository<OutboxEvent, EventId> {
  fun save(event: OutboxEvent)
}
