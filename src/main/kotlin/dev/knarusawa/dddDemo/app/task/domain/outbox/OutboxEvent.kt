package dev.knarusawa.dddDemo.app.task.domain.outbox

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.Type
import java.time.LocalDateTime

@Entity
@Table(name = "ddd_task_outbox")
class OutboxEvent private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("event_id"))
  val eventId: EventId,
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  val eventType: EventType,
  @Type(JsonType::class)
  @Column(nullable = false, columnDefinition = "jsonb")
  val payload: String,
  @Column(nullable = false)
  val occurredAt: LocalDateTime = LocalDateTime.now(),
  @Column(nullable = true)
  private var processedAt: LocalDateTime? = null,
) {
  companion object {
    fun of(
      type: EventType,
      payload: String,
    ) = OutboxEvent(
      eventId = EventId.new(),
      eventType = type,
      payload = payload,
    )
  }

  fun process() {
    this.processedAt = LocalDateTime.now()
  }
}
