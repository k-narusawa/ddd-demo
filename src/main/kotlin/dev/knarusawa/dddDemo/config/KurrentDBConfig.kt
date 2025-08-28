package dev.knarusawa.dddDemo.config

import io.kurrent.dbclient.KurrentDBClient
import io.kurrent.dbclient.KurrentDBClientSettings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KurrentDBConfig {
  @Bean
  fun kurrentDBClient(): KurrentDBClient {
    val settings =
      KurrentDBClientSettings
        .builder()
        .addHost("localhost", 2113)
        .tls(false)
        .buildConnectionSettings()

    return KurrentDBClient.create(settings)
  }
}
