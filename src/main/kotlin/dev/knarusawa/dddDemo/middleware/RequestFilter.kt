package dev.knarusawa.dddDemo.middleware

import dev.knarusawa.dddDemo.util.logger
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

@Component
class RequestFilter : OncePerRequestFilter() {
  companion object {
    val excludeUrlRegexList =
      listOf(
        Regex(".*webjars.*"),
        Regex(".*csv.*"),
        Regex(".*\\.png"),
      )
    private val log = logger()
  }

  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain,
  ) {
    val requestId = UUID.randomUUID().toString()
    MDC.put("requestId", requestId)

    val requestURI = request.requestURI
    val queryString = request.queryString

    log.info("リクエスト メソッド:[${request.method}] URI:[$requestURI] Query:[$queryString]")

    val start = System.currentTimeMillis()
    try {
      filterChain.doFilter(request, response)
    } finally {
      val end = System.currentTimeMillis()
      log.info(
        "レスポンス メソッド:[${request.method}] URI:[$requestURI] ステータス:[${response.status}] レスポンスタイム:[${end - start}ms]",
      )
      MDC.clear()
    }
  }

  override fun shouldNotFilter(request: HttpServletRequest): Boolean {
    val path = request.requestURI
    return excludeUrlRegexList.any { path.matches(it) }
  }
}
