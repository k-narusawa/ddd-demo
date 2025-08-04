package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.registerUser

import dev.k_narusawa.ddd_demo.app.identity_access.application.port.SignupUserInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserRepository
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserService
import dev.k_narusawa.ddd_demo.app.identity_access.exception.SignupException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignupUserInteractor(
  private val userService: UserService,
  private val userRepository: UserRepository,
) : SignupUserInputBoundary {
  @Transactional
  override suspend fun handle(input: SignupUserInputData): SignupUserOutputData {
    val canSignup = userService.canSignup(username = input.username)
    if (canSignup.not()) throw SignupException(message = "", username = input.username)

    val user = User.signup(username = input.username, password = input.password)
    userRepository.save(user)
    return SignupUserOutputData.of(
      userId = user.userId,
      username = input.username,
    )
  }
}
