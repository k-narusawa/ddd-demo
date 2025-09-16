package dev.knarusawa.dddDemo.config

import dev.knarusawa.dddDemo.util.JsonUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.PublishSubscribeChannel

@Configuration
class AppConfig {
  @Bean
  fun inputMessageChannel() = PublishSubscribeChannel()

  @Bean
  fun objectMapper() = JsonUtil.objectMapper
}
