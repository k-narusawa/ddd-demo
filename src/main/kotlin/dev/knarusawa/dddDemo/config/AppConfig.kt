package dev.knarusawa.dddDemo.config

import com.fasterxml.jackson.databind.ObjectMapper
import dev.knarusawa.dddDemo.util.JsonUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.PublishSubscribeChannel

@Configuration
class AppConfig {
  fun inputMessageChannel() = PublishSubscribeChannel()

  @Bean
  fun objectMapper(): ObjectMapper = JsonUtil.objectMapper
}
