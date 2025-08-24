package dev.k_narusawa.ddd_demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.event.TransactionalEventListenerFactory


@Configuration
@EnableTransactionManagement
class TransactionalConfig {
  @Bean
  fun transactionalEventListenerFactory(): TransactionalEventListenerFactory {
    return TransactionalEventListenerFactory()
  }
}
