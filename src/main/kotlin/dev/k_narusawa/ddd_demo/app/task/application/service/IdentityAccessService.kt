package dev.k_narusawa.ddd_demo.app.task.application.service

import dev.k_narusawa.ddd_demo.app.task.application.dto.IntrospectResultDto

interface IdentityAccessService {
    suspend fun introspect(token: String): IntrospectResultDto
}
