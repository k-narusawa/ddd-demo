package dev.knarusawa.dddDemo.app.task.domain.task

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, TaskId>
