package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.login

import dev.k_narusawa.ddd_demo.app.identity_access.application.port.LoginInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserRepository
import dev.k_narusawa.ddd_demo.app.identity_access.exception.AuthenticationException
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service

@Service
@EnableAsync
class LoginInteractor(
  private val userRepository: UserRepository,
) : LoginInputBoundary {
  @Transactional
  override suspend fun handle(input: LoginInputData) {
    val user = userRepository.findByUsername(input.username)
      ?: throw AuthenticationException(message = "認証に失敗しました")

    try {
      user.verifyPassword(
        rawPassword = input.password,
        userAgent = input.userAgent,
        ipAddress = input.ipAddress
      )
    } catch (ex: AuthenticationException) {
      if (user.isLock()) {
        throw AuthenticationException(
          message = "アカウントロック中",
          cause = ex,
          userId = user.userId,
          isLock = true
        )
      }
      throw ex
    } finally {
      userRepository.save(user = user)
    }
  }
}