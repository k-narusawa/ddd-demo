package dev.k_narusawa.ddd_demo.app.task.domain.taskEvent

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskEventRepository : JpaRepository<TaskEvent, TaskEventId> {
}
