package dev.k_narusawa.ddd_demo.app.task.adapter.http

import dev.k_narusawa.ddd_demo.app.task.adapter.http.model.IntrospectionResponse
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient

@Component
class IdentityAccessClient(
  private val identityAccessWebClient: WebClient
) {
  suspend fun introspect(token: String): IntrospectionResponse? {
    val map = LinkedMultiValueMap<String, String>()
    map.add("token", token)

    return identityAccessWebClient.post()
      .uri("/api/identity_access/token/introspect")
      .bodyValue(map)
      .retrieve()
      .bodyToMono(IntrospectionResponse::class.java)
      .block()
  }
}
