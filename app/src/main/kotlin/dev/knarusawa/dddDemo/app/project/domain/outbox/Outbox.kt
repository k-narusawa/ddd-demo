package dev.knarusawa.dddDemo.app.project.domain.outbox

import dev.knarusawa.dddDemo.app.project.domain.DomainEvent
import dev.knarusawa.dddDemo.app.project.domain.EventId
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.LocalDateTime

@Entity
@Table(name = "ddd_outbox")
class Outbox private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("event_id"))
  val eventId: EventId,
  @Column(nullable = false, columnDefinition = "event_type")
  val eventType: String,
  @Column(nullable = false, columnDefinition = "jsonb")
  val event: String,
  @Column(nullable = false)
  val occurredAt: LocalDateTime = LocalDateTime.now(),
  @Column(nullable = true)
  private var processedAt: LocalDateTime? = null,
  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,
) {
  companion object {
    fun of(event: DomainEvent) =
      Outbox(
        eventId = event.eventId,
        eventType = event::class.simpleName ?: "unkown event",
        event = event.serialize(),
      )
  }

  fun process() {
    this.processedAt = LocalDateTime.now()
  }
}
