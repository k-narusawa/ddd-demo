package dev.k_narusawa.ddd_demo.http.controller

import dev.k_narusawa.ddd_demo.app.identity_access.application.port.IntrospectionInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.application.port.LoginInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.application.port.SignupUserInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.introspection.IntrospectionInputData
import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.login.LoginInputData
import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.signup.SignupUserInputData
import dev.k_narusawa.ddd_demo.app.identity_access.domain.exception.TokenUnauthorized
import dev.k_narusawa.ddd_demo.http.model.IntrospectionResponse
import dev.k_narusawa.ddd_demo.http.model.LoginRequest
import dev.k_narusawa.ddd_demo.http.model.LoginResponse
import dev.k_narusawa.ddd_demo.http.model.UserRegistrationRequest
import dev.k_narusawa.ddd_demo.http.model.UserRegistrationResponse
import dev.k_narusawa.ddd_demo.util.logger
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.env.Environment
import org.springframework.core.env.getProperty
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class IdentityAccessController(
  private val environment: Environment,
  private val signupUserInputBoundary: SignupUserInputBoundary,
  private val loginInputBoundary: LoginInputBoundary,
  private val introspectionInputBoundary: IntrospectionInputBoundary,
) {
  companion object {
    private val log = logger()
  }

  @PostMapping("/users")
  suspend fun postUsers(
    @RequestBody
    userRegistrationRequest: UserRegistrationRequest
  ): ResponseEntity<UserRegistrationResponse> {
    val inputData = SignupUserInputData.of(
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

  @PostMapping("/token/introspect")
  suspend fun postIntrospection(
    @RequestBody
    request: MultiValueMap<String, String>
  ): ResponseEntity<IntrospectionResponse> {
    val token = request.getFirst("token") ?: throw RuntimeException()
    val accessTokenSecret =
      environment.getProperty<String>("environment.identity_access.access_token.secret")

    try {
      val input = IntrospectionInputData.of(
        accessToken = token,
        secret = accessTokenSecret!!
      )
      val output = introspectionInputBoundary.handle(input = input)
      return ResponseEntity.ok(output.response)
    } catch (ex: TokenUnauthorized) {
      log.warn("トークンの検証に失敗", ex)
      return ResponseEntity.ok(IntrospectionResponse(active = false, sub = null))
    }
  }
}
