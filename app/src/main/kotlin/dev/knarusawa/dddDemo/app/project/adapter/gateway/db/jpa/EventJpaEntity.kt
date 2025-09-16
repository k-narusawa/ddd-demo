package dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa

import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEvent
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime

@Entity
@Table(name = "ddd_project_event")
class EventJpaEntity private constructor(
  @Id
  @Column(nullable = false, name = "event_id")
  val eventId: String,
  @Column(nullable = false, name = "aggregate_id")
  val aggregateId: String,
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "aggregate_type")
  val aggregateType: AggregateType,
  @Column(nullable = false, name = "event_type")
  val eventType: String,
  @JdbcTypeCode(SqlTypes.BINARY)
  @Column(nullable = false, name = "event_data")
  val eventData: ByteArray,
  @Column(nullable = false, name = "occurred_at")
  val occurredAt: LocalDateTime,
) {
  companion object {
    fun of(event: TaskEvent): EventJpaEntity =
      EventJpaEntity(
        eventId = event.eventId.get(),
        aggregateId = event.taskId.get(),
        aggregateType = AggregateType.TASK,
        eventType = event.type.name,
        eventData = event.toEventMessage().toByteArray(),
        occurredAt = event.occurredAt,
      )

//    fun of(event: MemberEvent): EventJpaEntity =
//      EventJpaEntity(
//        eventId = event.eventId.get(),
//        aggregateId = event.memberId.get(),
//        aggregateType = AggregateType.MEMBER,
//        eventType = event.eventType.name,
//        eventData = event.toPayload(),
//        occurredAt = event.occurredAt,
//      )
  }
}
