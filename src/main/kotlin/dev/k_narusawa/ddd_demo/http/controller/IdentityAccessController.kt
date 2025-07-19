package dev.k_narusawa.ddd_demo.http.controller

import dev.k_narusawa.ddd_demo.app.identity_access.application.port.RegisterUserInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.registerUser.RegisterUserInputData
import dev.k_narusawa.ddd_demo.http.model.UserRegistrationRequest
import dev.k_narusawa.ddd_demo.http.model.UserRegistrationResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class IdentityAccessController(
  private val registerUserInputBoundary: RegisterUserInputBoundary
) {
  @PostMapping
  suspend fun post(
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
}