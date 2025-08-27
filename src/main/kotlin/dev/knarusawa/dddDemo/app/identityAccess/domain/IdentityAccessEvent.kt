package dev.knarusawa.dddDemo.app.identityAccess.domain

import org.springframework.context.ApplicationEvent
import java.time.LocalDateTime

open class IdentityAccessEvent(
  val occurredAt: LocalDateTime = LocalDateTime.now(),
  source: Any,
) : ApplicationEvent(source)
