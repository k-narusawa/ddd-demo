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

@Configuration("projectGcpPubSubConfig")
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
      PubSubInboundChannelAdapter(
        pubSubTemplate,
        PubSubModel.PROJECT_TASK_EVENT_SUBSCRIPTION,
      )
    adapter.setOutputChannel(messageChannel)
    adapter.ackMode = AckMode.MANUAL
    adapter.payloadType = String::class.java
    return adapter
  }

  @Bean
  fun projectUserEventSubscriptionChannel() = PublishSubscribeChannel()

  @Bean
  fun projectUserEventSubscriptionChannelAdapter(
    @Qualifier("projectUserEventSubscriptionChannel")
    messageChannel: MessageChannel,
    pubSubTemplate: PubSubTemplate,
  ): PubSubInboundChannelAdapter {
    val adapter =
      PubSubInboundChannelAdapter(
        pubSubTemplate,
        PubSubModel.PROJECT_USER_EVENT_SUBSCRIPTION,
      )
    adapter.setOutputChannel(messageChannel)
    adapter.ackMode = AckMode.MANUAL
    adapter.payloadType = String::class.java
    return adapter
  }

  @Bean
  fun projectEventPublishChannel() = PublishSubscribeChannel()

  @Bean
  @ServiceActivator(inputChannel = "projectEventPublishChannel")
  fun projectEventSender(pubSubTemplate: PubSubTemplate): MessageHandler {
    val adapter = PubSubMessageHandler(pubSubTemplate, PubSubModel.PROJECT_EVENT_TOPIC)

    adapter.setSuccessCallback { ackId: String, message: Message<*> ->
      log.debug("送信に成功したメッセージ message:$message")
      log.info("projectEventの送信に成功 ackId:$ackId")
    }

    adapter.setFailureCallback { cause: Throwable, message: Message<*> ->
      log.debug("送信に失敗したメッセージ message:$message")
      log.error("projectEventの送信に失敗", cause)
    }

    return adapter
  }

  @Bean
  fun taskEventPublishChannel() = PublishSubscribeChannel()

  @Bean
  @ServiceActivator(inputChannel = "taskEventPublishChannel")
  fun taskEventSender(pubSubTemplate: PubSubTemplate): MessageHandler {
    val adapter = PubSubMessageHandler(pubSubTemplate, PubSubModel.TASK_EVENT_TOPIC)

    adapter.setSuccessCallback { ackId: String, message: Message<*> ->
      log.debug("送信に成功したメッセージ message:$message")
      log.info("ProjectEventの送信に成功 ackId:$ackId")
    }

    adapter.setFailureCallback { cause: Throwable, message: Message<*> ->
      log.debug("送信に失敗したメッセージ message:$message")
      log.error("ProjectEventの送信に失敗", cause)
    }

    return adapter
  }
}
