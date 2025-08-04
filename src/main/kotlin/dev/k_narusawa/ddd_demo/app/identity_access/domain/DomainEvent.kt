package dev.k_narusawa.ddd_demo.app.identity_access.domain

import org.springframework.context.ApplicationEvent
import java.time.LocalDateTime

abstract class DomainEvent(
  val occurredAt: LocalDateTime = LocalDateTime.now(),
  source: Any
) : ApplicationEvent(source) {
}
