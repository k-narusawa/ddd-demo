package dev.k_narusawa.ddd_demo.app.identity_access.domain.user

import dev.k_narusawa.ddd_demo.app.identity_access.domain.loginAttempt.LoginAttempt
import dev.k_narusawa.ddd_demo.app.identity_access.domain.loginAttempt.LoginAttemptRepository
import dev.k_narusawa.ddd_demo.app.identity_access.domain.token.Token
import dev.k_narusawa.ddd_demo.app.identity_access.domain.token.TokenService
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginFailedEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.LoginSucceededEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.publisher.LoginFailedEventPublisher
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event.publisher.LoginSucceededEventPublisher
import dev.k_narusawa.ddd_demo.app.identity_access.exception.AuthenticationException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService(
  private val userRepository: UserRepository,
  private val loginAttemptRepository: LoginAttemptRepository,
  private val tokenService: TokenService,
) {
  fun login(
    username: Username,
    password: String,
    userAgent: String,
    ipAddress: String,
  ): Token {
    val user = userRepository.findByUsername(username = username)
      ?: throw AuthenticationException(message = "認証に失敗しました")

    try {
      user.verifyPassword(
        rawPassword = password,
        userAgent = userAgent,
        ipAddress = ipAddress
      )
    } catch (ex: AuthenticationException) {
      val event = LoginFailedEvent(
        user = user,
        userAgent = userAgent,
        ipAddress = ipAddress
      )
      LoginFailedEventPublisher.publish(event = event)
      val attempt = loginAttemptRepository.findByUserId(userId = user.userId)
        ?: LoginAttempt.new(userId = user.userId)
      if (attempt.isLocked()) {
        throw AuthenticationException(
          message = "アカウントロック中",
          cause = ex,
          userId = user.userId,
          isLock = true
        )
      }
      throw ex
    }

    val event = LoginSucceededEvent(
      user = user,
      userAgent = userAgent,
      ipAddress = ipAddress
    )
    LoginSucceededEventPublisher.publish(event = event)

    return tokenService.generateToken(userId = user.userId)
  }
}
