package dev.k_narusawa.ddd_demo.app.identity_access.adapter.controller

import dev.k_narusawa.ddd_demo.app.identity_access.adapter.controller.model.UserRegistrationRequest
import dev.k_narusawa.ddd_demo.app.identity_access.adapter.controller.model.UserRegistrationResponse
import dev.k_narusawa.ddd_demo.app.identity_access.application.port.SignupUserInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.signup.SignupUserInputData
import dev.k_narusawa.ddd_demo.util.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/identity_access")
class SignupController(
  private val signupUserInputBoundary: SignupUserInputBoundary,
) {
  companion object {
    private val log = logger()
  }

  @PostMapping("/users")
  suspend fun postUsers(
    @RequestBody
    userRegistrationRequest: UserRegistrationRequest
  ): ResponseEntity<UserRegistrationResponse> {
    val inputData = SignupUserInputData.Companion.of(
      username = userRegistrationRequest.username,
      password = userRegistrationRequest.password
    )
    val outputData = signupUserInputBoundary.handle(inputData)

    val response = UserRegistrationResponse(
      userId = outputData.userId,
      username = outputData.username
    )
    return ResponseEntity.ok(response)
  }
}
