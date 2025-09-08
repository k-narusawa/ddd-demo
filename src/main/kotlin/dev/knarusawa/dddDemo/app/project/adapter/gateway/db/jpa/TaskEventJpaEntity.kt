package dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa

import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEvent
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEventType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "ddd_task_event")
class TaskEventJpaEntity(
  @Id
  @Column(nullable = false, name = "task_event_id")
  val taskEventId: String,
  @Column(nullable = false, name = "task_id")
  val taskId: String,
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "type")
  val type: TaskEventType,
  @Column(nullable = false, name = "project_id")
  val projectId: String,
  @Column(nullable = false, name = "operator")
  val operator: String,
  @Column(nullable = false, name = "title")
  val title: String,
  @Column(nullable = true, name = "description")
  val description: String?,
  @Column(nullable = true, name = "assigner")
  val assigner: String?,
  @Column(nullable = true, name = "assignee")
  val assignee: String?,
  @Column(nullable = true, name = "from_time")
  val fromTime: LocalDateTime?,
  @Column(nullable = true, name = "to_time")
  val toTime: LocalDateTime?,
  @Column(nullable = false, name = "occurred_at")
  val occurredAt: LocalDateTime,
  @Column(nullable = false, name = "completed")
  val completed: Boolean,
) {
  companion object {
    fun from(event: TaskEvent): TaskEventJpaEntity =
      TaskEventJpaEntity(
        taskEventId = event.eventId.get(),
        taskId = event.taskId.get(),
        type = event.type,
        projectId = event.projectId.get(),
        operator = event.operator.get(),
        title = event.title.get(),
        description = event.description?.get(),
        assigner = event.assigner?.get(),
        assignee = event.assignee?.get(),
        fromTime = event.fromTime?.get(),
        toTime = event.toTime?.get(),
        occurredAt = event.occurredAt,
        completed = event.completed,
      )
  }
}
