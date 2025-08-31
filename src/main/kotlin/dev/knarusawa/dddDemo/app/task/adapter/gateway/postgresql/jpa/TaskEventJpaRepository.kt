package dev.knarusawa.dddDemo.app.task.adapter.gateway.postgresql.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface TaskEventJpaRepository : JpaRepository<TaskEventJpaEntity, String> {
  fun save(entity: TaskEventJpaEntity)

  fun findByTaskId(taskId: String): List<TaskEventJpaEntity>
}
