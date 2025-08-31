package dev.knarusawa.dddDemo.infrastructure

import com.google.cloud.spring.pubsub.core.PubSubTemplate
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler
import dev.knarusawa.dddDemo.util.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.PublishSubscribeChannel
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler

@Configuration
class GcpPubSubConfig {
  companion object {
    private val log = logger()
  }

  @Bean
  fun taskCreatedEventChannel() = PublishSubscribeChannel()

  @Bean
  fun taskChangedEventChannel() = PublishSubscribeChannel()

  @Bean
  fun taskCompletedEventChannel() = PublishSubscribeChannel()

  @Bean
  @ServiceActivator(inputChannel = "taskCreatedEventChannel")
  fun taskCreatedSender(pubSubTemplate: PubSubTemplate): MessageHandler {
    val adapter = PubSubMessageHandler(pubSubTemplate, PubSubModel.TASK_CREATED_TOPIC)

    adapter.setSuccessCallback { ackId: String, message: Message<*> ->
      log.info("タスク作成イベントの送信に成功 ackId:$ackId")
      log.debug("送信に成功したメッセージ message:$message")
    }

    adapter.setFailureCallback { cause: Throwable, message: Message<*> ->
      log.error("タスク作成イベントの送信に失敗", cause)
      log.debug("送信に失敗したメッセージ message:$message")
    }

    return adapter
  }

  @Bean
  @ServiceActivator(inputChannel = "taskChangedEventChannel")
  fun taskChangedSender(pubSubTemplate: PubSubTemplate): MessageHandler {
    val adapter = PubSubMessageHandler(pubSubTemplate, PubSubModel.TASK_CHANGED_TOPIC)

    adapter.setSuccessCallback { ackId: String, message: Message<*> ->
      log.info("タスク変更イベントの送信に成功 ackId:$ackId")
      log.debug("送信に成功したメッセージ message:$message")
    }

    adapter.setFailureCallback { cause: Throwable, message: Message<*> ->
      log.error("タスク変更イベントの送信に失敗", cause)
      log.debug("送信に失敗したメッセージ message:$message")
    }

    return adapter
  }

  @Bean
  @ServiceActivator(inputChannel = "taskCompletedEventChannel")
  fun taskCompletedSender(pubSubTemplate: PubSubTemplate): MessageHandler {
    val adapter = PubSubMessageHandler(pubSubTemplate, PubSubModel.TASK_COMPLETED_TOPIC)

    adapter.setSuccessCallback { ackId: String, message: Message<*> ->
      log.info("タスク完了イベントの送信に成功 ackId:$ackId")
      log.debug("送信に成功したメッセージ message:$message")
    }

    adapter.setFailureCallback { cause: Throwable, message: Message<*> ->
      log.error("タスク完了イベントの送信に失敗", cause)
      log.debug("送信に失敗したメッセージ message:$message")
    }

    return adapter
  }
}
