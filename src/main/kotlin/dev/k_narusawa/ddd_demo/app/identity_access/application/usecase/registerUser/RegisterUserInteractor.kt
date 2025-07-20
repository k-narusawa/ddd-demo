package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.registerUser

import dev.k_narusawa.ddd_demo.app.identity_access.application.port.RegisterUserInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.Password
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterUserInteractor(
  private val userRepository: UserRepository
): RegisterUserInputBoundary {
  @Transactional
  override suspend fun handle(input: RegisterUserInputData): RegisterUserOutputData {
    val password = Password.of(input.password)
    val user = User.register(username = input.username, password = password)
    userRepository.save(user)
    return RegisterUserOutputData.of(
      userId = user.userId.get().toString(),
      username = input.username.get(),
    )
  }
}