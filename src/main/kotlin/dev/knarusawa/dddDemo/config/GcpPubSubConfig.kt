package dev.knarusawa.dddDemo.config

import com.google.cloud.spring.pubsub.core.PubSubTemplate
import com.google.cloud.spring.pubsub.integration.AckMode
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler
import dev.knarusawa.dddDemo.infrastructure.PubSubModel
import dev.knarusawa.dddDemo.util.logger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.PublishSubscribeChannel
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler

@Configuration
class GcpPubSubConfig {
  companion object {
    private val log = logger()
  }

  @Bean
  fun taskCreatedSubscriptionChannel() = PublishSubscribeChannel()

  @Bean
  fun taskCreatedSubscriptionChannelAdapter(
    @Qualifier("taskCreatedSubscriptionChannel")
    messageChannel: MessageChannel,
    pubSubTemplate: PubSubTemplate,
  ): PubSubInboundChannelAdapter {
    val adapter =
      PubSubInboundChannelAdapter(pubSubTemplate, PubSubModel.Companion.TASK_CREATED_SUBSCRIPTION)
    adapter.setOutputChannel(messageChannel)
    adapter.ackMode = AckMode.MANUAL
    adapter.payloadType = String::class.java
    return adapter
  }

  @Bean
  fun taskChangedSubscriptionChannel() = PublishSubscribeChannel()

  @Bean
  fun taskChangedSubscriptionChannelAdapter(
    @Qualifier("taskChangedSubscriptionChannel")
    messageChannel: MessageChannel,
    pubSubTemplate: PubSubTemplate,
  ): PubSubInboundChannelAdapter {
    val adapter =
      PubSubInboundChannelAdapter(pubSubTemplate, PubSubModel.Companion.TASK_CHANGED_SUBSCRIPTION)
    adapter.setOutputChannel(messageChannel)
    adapter.ackMode = AckMode.MANUAL
    adapter.payloadType = String::class.java
    return adapter
  }

  @Bean
  fun taskCompletedSubscriptionChannel() = PublishSubscribeChannel()

  @Bean
  fun taskCompletedSubscriptionChannelAdapter(
    @Qualifier("taskCompletedSubscriptionChannel")
    messageChannel: MessageChannel,
    pubSubTemplate: PubSubTemplate,
  ): PubSubInboundChannelAdapter {
    val adapter =
      PubSubInboundChannelAdapter(pubSubTemplate, PubSubModel.Companion.TASK_COMPLETED_SUBSCRIPTION)
    adapter.setOutputChannel(messageChannel)
    adapter.ackMode = AckMode.MANUAL
    adapter.payloadType = String::class.java
    return adapter
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
    val adapter = PubSubMessageHandler(pubSubTemplate, PubSubModel.Companion.TASK_CREATED_TOPIC)

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
    val adapter = PubSubMessageHandler(pubSubTemplate, PubSubModel.Companion.TASK_CHANGED_TOPIC)

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
    val adapter = PubSubMessageHandler(pubSubTemplate, PubSubModel.Companion.TASK_COMPLETED_TOPIC)

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
