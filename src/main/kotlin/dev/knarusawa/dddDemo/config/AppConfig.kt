package dev.knarusawa.dddDemo.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
  @Bean
  fun objectMapper(): ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())
}
