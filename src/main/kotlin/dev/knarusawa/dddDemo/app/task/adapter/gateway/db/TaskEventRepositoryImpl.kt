package dev.knarusawa.dddDemo.app.task.adapter.gateway.db

import dev.knarusawa.dddDemo.app.task.adapter.gateway.db.jpa.TaskEventJpaEntity
import dev.knarusawa.dddDemo.app.task.adapter.gateway.db.jpa.TaskEventJpaRepository
import dev.knarusawa.dddDemo.app.task.domain.TaskEventType
import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCompleted
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEvent
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEventId
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEventRepository
import org.springframework.stereotype.Repository

@Repository
class TaskEventRepositoryImpl(
  private val taskEventJpaRepository: TaskEventJpaRepository,
) : TaskEventRepository {
  override fun save(event: TaskEvent) {
    val entity = TaskEventJpaEntity.from(event = event)
    taskEventJpaRepository.save(entity = entity)
  }

  override fun findByTaskIdOrderByOccurredAtAsc(taskId: TaskId): List<TaskEvent> {
    val entities = taskEventJpaRepository.findByTaskId(taskId = taskId.get())
    return entities.map {
      when (it.type) {
        TaskEventType.TASK_CREATED -> {
          TaskCreated(
            taskEventId = TaskEventId.from(value = it.taskEventId),
            taskId = taskId,
            type = it.type,
            projectId = ProjectId.from(value = it.projectId),
            operator = MemberId.from(value = it.operator),
            title = Title.of(value = it.title),
            description = it.description?.let { Description.of(value = it) },
            assigner = it.assigner?.let { MemberId.from(value = it) },
            assignee = it.assignee?.let { MemberId.from(value = it) },
            fromTime = it.fromTime?.let { FromTime.of(value = it) },
            toTime = it.toTime?.let { ToTime.of(value = it) },
            occurredAt = it.occurredAt,
            completed = it.completed,
            version = it.version,
          )
        }

        TaskEventType.TASK_CHANGED -> {
          TaskChanged(
            taskEventId = TaskEventId.from(value = it.taskEventId),
            taskId = taskId,
            type = it.type,
            projectId = ProjectId.from(value = it.projectId),
            operator = MemberId.from(value = it.operator),
            title = Title.of(value = it.title),
            description = it.description?.let { Description.of(value = it) },
            assigner = it.assigner?.let { MemberId.from(value = it) },
            assignee = it.assignee?.let { MemberId.from(value = it) },
            fromTime = it.fromTime?.let { FromTime.of(value = it) },
            toTime = it.toTime?.let { ToTime.of(value = it) },
            occurredAt = it.occurredAt,
            completed = it.completed,
            version = it.version,
          )
        }

        TaskEventType.TASK_COMPLETED -> {
          TaskCompleted(
            taskEventId = TaskEventId.from(value = it.taskEventId),
            taskId = taskId,
            type = it.type,
            projectId = ProjectId.from(value = it.projectId),
            operator = MemberId.from(value = it.operator),
            title = Title.of(value = it.title),
            description = it.description?.let { Description.of(value = it) },
            assigner = it.assigner?.let { MemberId.from(value = it) },
            assignee = it.assignee?.let { MemberId.from(value = it) },
            fromTime = it.fromTime?.let { FromTime.of(value = it) },
            toTime = it.toTime?.let { ToTime.of(value = it) },
            occurredAt = it.occurredAt,
            completed = it.completed,
            version = it.version,
          )
        }

        else -> throw IllegalStateException()
      }
    }
  }
}
