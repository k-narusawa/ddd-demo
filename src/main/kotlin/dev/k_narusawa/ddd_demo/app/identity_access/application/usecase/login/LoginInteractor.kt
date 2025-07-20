package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.login

import dev.k_narusawa.ddd_demo.app.identity_access.application.port.LoginInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserRepository
import org.springframework.stereotype.Service

@Service
class LoginInteractor(
  private val userRepository: UserRepository
) : LoginInputBoundary {
  override suspend fun handle(input: LoginInputData) {
    val user = userRepository.findByUsername(input.username)
      ?: throw IllegalArgumentException("User not found with username: ${input.username.get()}")

    if (!user.matchPassword(rawPassword = input.password)) {
      throw IllegalArgumentException("Invalid password for user: ${input.username.get()}")
    }
  }
}