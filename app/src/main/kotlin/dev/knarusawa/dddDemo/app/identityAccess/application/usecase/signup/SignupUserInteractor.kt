package dev.knarusawa.dddDemo.app.identityAccess.application.usecase.signup

import dev.knarusawa.dddDemo.app.identityAccess.application.DomainEventPublisher
import dev.knarusawa.dddDemo.app.identityAccess.application.exception.UsernameAlreadyExists
import dev.knarusawa.dddDemo.app.identityAccess.application.port.SignupUserInputBoundary
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserRepository
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SignupUserInteractor(
  private val userService: UserService,
  private val userRepository: UserRepository,
  private val eventPublisher: DomainEventPublisher,
) : SignupUserInputBoundary {
  override fun handle(input: SignupUserInputData): SignupUserOutputData {
    val canSignup = userService.isExists(username = input.username)
    if (canSignup.not()) throw UsernameAlreadyExists(message = "", username = input.username)

    val user =
      User.signup(
        username = input.username,
        password = input.password,
        givenName = input.givenName,
        familyName = input.familyName,
      )
    userRepository.save(user)

    user.getEvents().forEach {
      eventPublisher.publish(it)
    }

    return SignupUserOutputData.of(
      userId = user.userId,
      username = input.username,
    )
  }
}
