package dev.knarusawa.dddDemo.app.identityAccess.application.usecase.introspection

import dev.knarusawa.dddDemo.app.identityAccess.application.port.IntrospectionInputBoundary
import dev.knarusawa.dddDemo.app.identityAccess.domain.token.TokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class IntrospectionInteractor(
  private val tokenRepository: TokenRepository,
) : IntrospectionInputBoundary {
  override suspend fun handle(input: IntrospectionInputData): IntrospectionOutputData {
    val token =
      tokenRepository.findByAccessToken(accessToken = input.accessToken)
        ?: return IntrospectionOutputData.of(
          active = false,
        )

    if (!token.isActive()) {
      return IntrospectionOutputData.of(
        active = false,
      )
    }
    return IntrospectionOutputData.of(
      sub = token.userId,
      active = true,
    )
  }
}
