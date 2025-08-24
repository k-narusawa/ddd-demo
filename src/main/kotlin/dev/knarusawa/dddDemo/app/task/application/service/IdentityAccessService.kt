package dev.knarusawa.dddDemo.app.task.application.service

import dev.knarusawa.dddDemo.app.task.application.dto.IntrospectResultDto

interface IdentityAccessService {
  suspend fun introspect(token: String): IntrospectResultDto
}
