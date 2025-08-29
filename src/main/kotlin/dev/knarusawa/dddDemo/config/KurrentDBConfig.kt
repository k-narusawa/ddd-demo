package dev.knarusawa.dddDemo.config

import io.kurrent.dbclient.CreatePersistentSubscriptionToAllOptions
import io.kurrent.dbclient.KurrentDBClient
import io.kurrent.dbclient.KurrentDBClientSettings
import io.kurrent.dbclient.KurrentDBPersistentSubscriptionsClient
import io.kurrent.dbclient.SubscriptionFilter
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

  @Bean("taskProjection")
  fun taskProjectionSubscriptionClient(): KurrentDBPersistentSubscriptionsClient {
    val settings =
      KurrentDBClientSettings
        .builder()
        .addHost("localhost", 2113)
        .tls(false)
        .buildConnectionSettings()

    return KurrentDBPersistentSubscriptionsClient.create(settings).apply {
      val filter =
        SubscriptionFilter
          .newBuilder()
          .addStreamNamePrefix("task_event-")
          .build()

      createToAll(
        "TaskProjectionGroup",
        CreatePersistentSubscriptionToAllOptions
          .get()
          .filter(filter)
          .fromEnd(),
      )
    }
  }
}
