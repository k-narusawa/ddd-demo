package dev.knarusawa.dddDemo.middleware

import dev.knarusawa.dddDemo.util.logger
import org.slf4j.MDC
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.UUID

@Component
@Order(-1)
class WebFluxRequestFilter : WebFilter {
  companion object {
    private val log = logger()
    const val REQUEST_ID_KEY = "requestId"
  }

  override fun filter(
    exchange: ServerWebExchange,
    chain: WebFilterChain,
  ): Mono<Void> {
    val start = System.currentTimeMillis()
    val requestId =
      exchange.request.headers.getFirst(REQUEST_ID_KEY) ?: UUID.randomUUID().toString()

    exchange.response.headers.add(REQUEST_ID_KEY, requestId)

    return chain
      .filter(exchange)
      .doFinally { signalType ->
        val end = System.currentTimeMillis()
        val duration = end - start
        val request = exchange.request
        val response = exchange.response

        try {
          MDC.put(REQUEST_ID_KEY, requestId)
          log.info(
            "-- END -- [${request.method.name()}] ${request.uri.path} status:[${response.statusCode?.value()}] time:[${duration}ms]",
          )
        } finally {
          MDC.remove(REQUEST_ID_KEY)
        }
      }.doOnSubscribe { subscription ->
        try {
          MDC.put(REQUEST_ID_KEY, requestId)
          log.info("--START-- [${exchange.request.method.name()}] ${exchange.request.uri.path}")
        } finally {
          MDC.remove(REQUEST_ID_KEY)
        }
      }
  }
}
