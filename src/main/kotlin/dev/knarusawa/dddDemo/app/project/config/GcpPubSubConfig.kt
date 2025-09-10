package dev.knarusawa.dddDemo.app.project.config

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
  fun taskEventSubscriptionChannel() = PublishSubscribeChannel()

  @Bean
  fun taskEventSubscriptionChannelAdapter(
    @Qualifier("taskEventSubscriptionChannel")
    messageChannel: MessageChannel,
    pubSubTemplate: PubSubTemplate,
  ): PubSubInboundChannelAdapter {
    val adapter =
      PubSubInboundChannelAdapter(pubSubTemplate, PubSubModel.Companion.TASK_EVENT_SUBSCRIPTION)
    adapter.setOutputChannel(messageChannel)
    adapter.ackMode = AckMode.MANUAL
    adapter.payloadType = String::class.java
    return adapter
  }

  @Bean
  fun taskEventChannel() = PublishSubscribeChannel()

  @Bean
  @ServiceActivator(inputChannel = "taskEventChannel")
  fun taskEventSender(pubSubTemplate: PubSubTemplate): MessageHandler {
    val adapter = PubSubMessageHandler(pubSubTemplate, PubSubModel.Companion.TASK_EVENT_TOPIC)

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
}
