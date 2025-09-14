package dev.knarusawa.dddDemo.middleware

import dev.knarusawa.dddDemo.infrastructure.RequestId
import dev.knarusawa.dddDemo.util.logger
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
@Order(-1)
class WebFluxRequestFilter : WebFilter {
  companion object {
    private val log = logger()
  }

  override fun filter(
    exchange: ServerWebExchange,
    chain: WebFilterChain,
  ): Mono<Void> {
    val start = System.currentTimeMillis()
    val originalRequestId =
      exchange.request.headers.getFirst(RequestId.HEADER_KEY)

    if (originalRequestId != null) {
      RequestId.set(value = originalRequestId)
    } else {
      RequestId.set()
    }

    exchange.response.headers.add(RequestId.HEADER_KEY, RequestId.get())

    return chain
      .filter(exchange)
      .doFinally { signalType ->
        val end = System.currentTimeMillis()
        val duration = end - start
        val request = exchange.request
        val response = exchange.response
        log.info(
          "-- END -- [${request.method.name()}] ${request.uri.path} status:[${response.statusCode?.value()}] time:[${duration}ms]",
        )
      }.doOnSubscribe { subscription ->
        log.info("--START-- [${exchange.request.method.name()}] ${exchange.request.uri.path}")
      }.contextWrite { context ->
        context.put(RequestId.HEADER_KEY, RequestId.get())
      }
  }
}
