package dev.knarusawa.dddDemo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.event.TransactionalEventListenerFactory

@Configuration
@EnableTransactionManagement
class TransactionalConfig {
  @Bean
  fun transactionalEventListenerFactory(): TransactionalEventListenerFactory =
    TransactionalEventListenerFactory()
}
