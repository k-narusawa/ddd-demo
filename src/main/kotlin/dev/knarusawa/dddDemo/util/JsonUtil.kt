package dev.knarusawa.dddDemo.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue

object JsonUtil {
  val objectMapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())

  fun jsonToMap(jsonString: String): Map<String, Any> = objectMapper.readValue(jsonString)
}
