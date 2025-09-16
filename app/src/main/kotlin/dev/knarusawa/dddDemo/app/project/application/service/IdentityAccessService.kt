package dev.knarusawa.dddDemo.app.project.application.service

import dev.knarusawa.dddDemo.app.project.application.dto.IntrospectResultDto

interface IdentityAccessService {
  suspend fun introspect(token: String): IntrospectResultDto
}
