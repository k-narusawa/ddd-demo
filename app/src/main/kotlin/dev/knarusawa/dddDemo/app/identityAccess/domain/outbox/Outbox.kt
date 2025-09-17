package dev.knarusawa.dddDemo.app.identityAccess.domain.outbox

import dev.knarusawa.dddDemo.app.identityAccess.domain.DomainEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.EventId
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UserEvent
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
    fun of(event: DomainEvent<*>): Outbox =
      when (event) {
        is UserEvent -> {
          Outbox(
            eventId = event.eventId,
            eventType = event.type.name,
            event = event.serialize(),
          )
        }

        else -> throw IllegalStateException("予期せぬイベント event: ${event::class.simpleName}")
      }
  }

  fun process() {
    this.processedAt = LocalDateTime.now()
  }
}
