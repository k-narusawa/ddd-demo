package dev.k_narusawa.ddd_demo.http.controller

import dev.k_narusawa.ddd_demo.app.identity_access.application.port.LoginInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.application.port.RegisterUserInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.login.LoginInputData
import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.registerUser.RegisterUserInputData
import dev.k_narusawa.ddd_demo.http.model.LoginRequest
import dev.k_narusawa.ddd_demo.http.model.LoginResponse
import dev.k_narusawa.ddd_demo.http.model.UserRegistrationRequest
import dev.k_narusawa.ddd_demo.http.model.UserRegistrationResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class IdentityAccessController(
  private val registerUserInputBoundary: RegisterUserInputBoundary,
  private val loginInputBoundary: LoginInputBoundary
) {
  @PostMapping("/users")
  suspend fun postUsers(
    @RequestBody
    userRegistrationRequest: UserRegistrationRequest
  ): ResponseEntity<UserRegistrationResponse> {
    val inputData = RegisterUserInputData.of(
      username = userRegistrationRequest.username,
      password = userRegistrationRequest.password
    )
    val outputData = registerUserInputBoundary.handle(inputData)

    val response = UserRegistrationResponse(
      userId = outputData.userId,
      username = outputData.username
    )
    return ResponseEntity.ok(response)
  }

  @PostMapping("/login")
  suspend fun postLogin(
    request: HttpServletRequest,
    @RequestHeader(name = HttpHeaders.USER_AGENT, required = false)
    userAgent: String,
    @RequestBody
    requestBody: LoginRequest
  ): ResponseEntity<LoginResponse> {
    val input = LoginInputData.of(
      username = requestBody.username,
      password = requestBody.password,
      userAgent = userAgent,
      remoteAddr = request.remoteAddr
    )
    val output = loginInputBoundary.handle(input = input)
    return ResponseEntity.ok(output.response)
  }
}
