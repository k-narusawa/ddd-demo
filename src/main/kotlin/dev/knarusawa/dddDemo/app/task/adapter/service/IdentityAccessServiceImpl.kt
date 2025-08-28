package dev.knarusawa.dddDemo.app.task.adapter.service

import dev.knarusawa.dddDemo.app.task.adapter.gateway.http.IdentityAccessClient
import dev.knarusawa.dddDemo.app.task.application.dto.IntrospectResultDto
import dev.knarusawa.dddDemo.app.task.application.service.IdentityAccessService
import org.springframework.stereotype.Service

@Service
class IdentityAccessServiceImpl(
  private val identityAccessClient: IdentityAccessClient,
) : IdentityAccessService {
  override suspend fun introspect(token: String): IntrospectResultDto {
    val resp = identityAccessClient.introspect(token = token)

    if (resp == null) {
      return IntrospectResultDto(
        sub = null,
        active = false,
      )
    }

    return IntrospectResultDto.of(resp = resp)
  }
}
