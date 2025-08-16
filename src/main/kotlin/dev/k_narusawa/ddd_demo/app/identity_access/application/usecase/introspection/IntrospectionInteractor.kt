package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.introspection

import dev.k_narusawa.ddd_demo.app.identity_access.application.port.IntrospectionInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.domain.token.TokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class IntrospectionInteractor(
  private val tokenRepository: TokenRepository
) : IntrospectionInputBoundary {
  override suspend fun handle(input: IntrospectionInputData): IntrospectionOutputData {
    val token = tokenRepository.findByAccessToken(accessToken = input.accessToken)
      ?: return IntrospectionOutputData.of(
        active = false
      )

    if (!token.isActive()) {
      return IntrospectionOutputData.of(
        active = false
      )
    }
    return IntrospectionOutputData.of(
      sub = token.userId,
      active = true
    )
  }
}
