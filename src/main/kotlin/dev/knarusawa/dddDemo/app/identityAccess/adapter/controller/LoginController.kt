package dev.knarusawa.dddDemo.app.identityAccess.adapter.controller

import dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model.LoginRequest
import dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model.LoginResponse
import dev.knarusawa.dddDemo.app.identityAccess.application.port.LoginInputBoundary
import dev.knarusawa.dddDemo.app.identityAccess.application.usecase.login.LoginInputData
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/identity_access/login")
class LoginController(
  private val loginInputBoundary: LoginInputBoundary,
) {
  @PostMapping()
  suspend fun postLogin(
    request: HttpServletRequest,
    @RequestHeader(name = HttpHeaders.USER_AGENT, required = false)
    userAgent: String,
    @RequestBody
    requestBody: LoginRequest,
  ): ResponseEntity<LoginResponse> {
    val input =
      LoginInputData.of(
        username = requestBody.username,
        password = requestBody.password,
        userAgent = userAgent,
        remoteAddr = request.remoteAddr,
      )
    val output = loginInputBoundary.handle(input = input)
    return ResponseEntity.ok(output.response)
  }
}
