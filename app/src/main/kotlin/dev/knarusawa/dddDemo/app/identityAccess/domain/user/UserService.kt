package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import dev.knarusawa.dddDemo.app.identityAccess.application.DomainEventPublisher
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.AccountLock
import dev.knarusawa.dddDemo.app.identityAccess.domain.token.Token
import dev.knarusawa.dddDemo.app.identityAccess.domain.token.TokenService
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.LoginFailed
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.LoginSucceeded
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.LoginFailed as LoginFailedException

@Service
@Transactional(noRollbackFor = [LoginFailedException::class, AccountLock::class])
class UserService(
  private val userRepository: UserRepository,
  private val tokenService: TokenService,
  private val eventPublisher: DomainEventPublisher,
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
        ?: throw LoginFailedException(message = "認証に失敗しました")

    try {
      user.verifyPassword(rawPassword = password)
    } catch (ex: LoginFailedException) {
      user.loginFailed()
      userRepository.save(user = user)

      val event =
        LoginFailed.of(
          user = user,
          userAgent = userAgent,
          ipAddress = ipAddress,
        )
      eventPublisher.publish(event)

      if (user.isLocked()) {
        throw AccountLock()
      }
      throw ex
    }

    user.unlock()
    userRepository.save(user = user)

    val event =
      LoginSucceeded.of(
        user = user,
        userAgent = userAgent,
        ipAddress = ipAddress,
      )
    eventPublisher.publish(event)

    return tokenService.generateToken(userId = user.userId)
  }
}
