package dev.k_narusawa.ddd_demo.app.task.domain.task

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, TaskId> {
}