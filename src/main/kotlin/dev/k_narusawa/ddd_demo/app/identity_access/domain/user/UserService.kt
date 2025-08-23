package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import dev.k_narusawa.ddd_demo.app.identity_access.domain.exception.AccountLock
import dev.k_narusawa.ddd_demo.app.identity_access.domain.exception.LoginFailed
import dev.k_narusawa.ddd_demo.app.identity_access.domain.loginAttempt.LoginAttempt
import dev.k_narusawa.ddd_demo.app.identity_access.domain.loginAttempt.LoginAttemptRepository
import dev.k_narusawa.ddd_demo.app.identity_access.domain.token.Token
import dev.k_narusawa.ddd_demo.app.identity_access.domain.token.TokenService
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginFailedDomainEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginSucceededDomainEvent
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService(
  private val userRepository: UserRepository,
  private val loginAttemptRepository: LoginAttemptRepository,
  private val tokenService: TokenService,
  private val applicationEventPublisher: ApplicationEventPublisher
) {
  fun isExists(username: Username): Boolean {
    val existsUser = userRepository.findByUsername(username = username)
    return existsUser == null
  }

  fun login(
    username: Username,
    password: String,
    userAgent: String,
    ipAddress: String,
  ): Token {
    val user = userRepository.findByUsername(username = username)
      ?: throw LoginFailed(message = "認証に失敗しました")

    try {
      user.verifyPassword(rawPassword = password)
    } catch (ex: LoginFailed) {
      val event = LoginFailedDomainEvent(
        user = user,
        userAgent = userAgent,
        ipAddress = ipAddress
      )
      applicationEventPublisher.publishEvent(event)
      val attempt = loginAttemptRepository.findByUserId(userId = user.userId)
        ?: LoginAttempt.new(userId = user.userId)
      if (attempt.isLocked()) {
        throw AccountLock(
          cause = ex,
          userId = user.userId,
        )
      }
      throw ex
    }

    val event = LoginSucceededDomainEvent(
      user = user,
      userAgent = userAgent,
      ipAddress = ipAddress
    )
    applicationEventPublisher.publishEvent(event)

    return tokenService.generateToken(userId = user.userId)
  }
}
