package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.AccountLock
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.LoginFailed
import dev.knarusawa.dddDemo.app.identityAccess.domain.token.Token
import dev.knarusawa.dddDemo.app.identityAccess.domain.token.TokenService
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.LoginFailedDomainEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.LoginSucceededDomainEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(noRollbackFor = [LoginFailed::class, AccountLock::class])
class UserService(
  private val userRepository: UserRepository,
  private val tokenService: TokenService,
  private val eventPublisher: ApplicationEventPublisher,
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
    val user =
      userRepository.findByUsername(username = username)
        ?: throw LoginFailed(message = "認証に失敗しました")

    try {
      user.verifyPassword(rawPassword = password)
    } catch (ex: LoginFailed) {
      user.loginFailed()
      userRepository.save(user = user)

      val event =
        LoginFailedDomainEvent(
          user = user,
          userAgent = userAgent,
          ipAddress = ipAddress,
        )
      eventPublisher.publishEvent(event)

      if (user.isLocked()) {
        throw AccountLock()
      }
      throw ex
    }

    user.unlock()
    userRepository.save(user = user)

    val event =
      LoginSucceededDomainEvent(
        user = user,
        userAgent = userAgent,
        ipAddress = ipAddress,
      )
    eventPublisher.publishEvent(event)

    return tokenService.generateToken(userId = user.userId)
  }
}
