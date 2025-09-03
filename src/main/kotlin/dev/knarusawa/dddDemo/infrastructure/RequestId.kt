package dev.knarusawa.dddDemo.infrastructure

import org.slf4j.MDC
import java.util.UUID

class RequestId {
  companion object {
    const val HEADER_KEY = "X-Request-Id"

    private fun generate() = UUID.randomUUID().toString()

    fun set() = MDC.put(HEADER_KEY, generate())

    fun set(value: String) = MDC.put(HEADER_KEY, value)

    fun get() = MDC.get(HEADER_KEY) ?: "RequestId is not set."

    fun clear() = MDC.remove(HEADER_KEY)
  }
}
