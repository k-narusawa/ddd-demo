package dev.k_narusawa.ddd_demo.app.task.domain

import org.springframework.context.ApplicationEvent
import java.time.LocalDateTime

open class TaskDomainEvent(
  val occurredAt: LocalDateTime = LocalDateTime.now(),
  source: Any
) : ApplicationEvent(source)
