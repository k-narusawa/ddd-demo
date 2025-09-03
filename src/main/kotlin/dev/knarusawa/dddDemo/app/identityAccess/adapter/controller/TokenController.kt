package dev.knarusawa.dddDemo.app.identityAccess.adapter.controller

import dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model.IntrospectionResponse
import dev.knarusawa.dddDemo.app.identityAccess.application.port.IntrospectionInputBoundary
import dev.knarusawa.dddDemo.app.identityAccess.application.usecase.introspection.IntrospectionInputData
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.TokenUnauthorized
import dev.knarusawa.dddDemo.util.logger
import org.springframework.core.env.Environment
import org.springframework.core.env.getProperty
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/identity_access/token")
class TokenController(
  private val environment: Environment,
  private val introspectionInputBoundary: IntrospectionInputBoundary,
) {
  companion object {
    private val log = logger()
  }

  @PostMapping("/introspect")
  suspend fun postIntrospection(
    @RequestBody
    request: MultiValueMap<String, String>,
  ): ResponseEntity<IntrospectionResponse> {
    val token = request.getFirst("token") ?: throw RuntimeException()
    val accessTokenSecret =
      environment.getProperty<String>("environment.identity_access.access_token.secret")

    try {
      val input =
        IntrospectionInputData.of(
          accessToken = token,
          secret = accessTokenSecret!!,
        )
      val output = introspectionInputBoundary.handle(input = input)
      return ResponseEntity.ok(output.response)
    } catch (ex: TokenUnauthorized) {
      log.warn("トークンの検証に失敗", ex)
      return ResponseEntity.ok(IntrospectionResponse(active = false, sub = null))
    }
  }
}
