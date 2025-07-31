package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.login

import dev.k_narusawa.ddd_demo.app.identity_access.application.port.LoginInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserService
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service

@Service
@EnableAsync
@Transactional
class LoginInteractor(
  private val userService: UserService,
) : LoginInputBoundary {
  @Transactional
  override suspend fun handle(input: LoginInputData) {
    userService.login(
      username = input.username,
      password = input.password,
      userAgent = input.userAgent,
      ipAddress = input.ipAddress,
    )
  }
}