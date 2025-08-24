package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.login

import dev.k_narusawa.ddd_demo.app.identity_access.application.port.LoginInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.domain.token.TokenRepository
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserService
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@EnableAsync
@Transactional
class LoginInteractor(
    private val userService: UserService,
    private val tokenRepository: TokenRepository,
) : LoginInputBoundary {
    override suspend fun handle(input: LoginInputData): LoginOutputData {
        val token =
            userService.login(
                username = input.username,
                password = input.password,
                userAgent = input.userAgent,
                ipAddress = input.ipAddress,
            )

        tokenRepository.save(token = token)
        return LoginOutputData.of(token = token)
    }
}
