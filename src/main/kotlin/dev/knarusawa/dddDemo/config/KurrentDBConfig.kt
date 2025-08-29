package dev.knarusawa.dddDemo.config

import dev.knarusawa.dddDemo.util.logger
import io.kurrent.dbclient.CreatePersistentSubscriptionToAllOptions
import io.kurrent.dbclient.KurrentDBClient
import io.kurrent.dbclient.KurrentDBClientSettings
import io.kurrent.dbclient.KurrentDBPersistentSubscriptionsClient
import io.kurrent.dbclient.SubscriptionFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KurrentDBConfig {
  companion object {
    private val log = logger()
  }

  @Bean
  fun kurrentDBClient(): KurrentDBClient {
    val settings =
      KurrentDBClientSettings
        .builder()
        .addHost("localhost", 2113)
        .tls(false)
        .buildConnectionSettings()

    log.info("KurrentDBClientの初期化")
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

    log.info("TaskProjectionSubscriptionClientの初期化")
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
