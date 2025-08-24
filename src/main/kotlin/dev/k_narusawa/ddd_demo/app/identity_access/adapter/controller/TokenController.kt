package dev.k_narusawa.ddd_demo.app.identity_access.adapter.controller

import dev.k_narusawa.ddd_demo.app.identity_access.adapter.controller.model.IntrospectionResponse
import dev.k_narusawa.ddd_demo.app.identity_access.application.port.IntrospectionInputBoundary
import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.introspection.IntrospectionInputData
import dev.k_narusawa.ddd_demo.app.identity_access.domain.exception.TokenUnauthorized
import dev.k_narusawa.ddd_demo.util.logger
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
                IntrospectionInputData.Companion.of(
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
