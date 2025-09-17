package dev.knarusawa.dddDemo.app.identityAccess.config

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

@Configuration("identityAccessGcpPubSubConfig")
class GcpPubSubConfig {
  companion object {
    private val log = logger()
  }

  @Bean
  fun userEventSubscriptionChannel() = PublishSubscribeChannel()

  @Bean
  fun userEventSubscriptionChannelAdapter(
    @Qualifier("userEventSubscriptionChannel")
    messageChannel: MessageChannel,
    pubSubTemplate: PubSubTemplate,
  ): PubSubInboundChannelAdapter {
    val adapter =
      PubSubInboundChannelAdapter(
        pubSubTemplate,
        PubSubModel.IDENTITY_ACCESS_USER_EVENT_SUBSCRIPTION,
      )
    adapter.setOutputChannel(messageChannel)
    adapter.ackMode = AckMode.MANUAL
    adapter.payloadType = String::class.java
    return adapter
  }

  @Bean
  fun userEventChannel() = PublishSubscribeChannel()

  @Bean
  @ServiceActivator(inputChannel = "userEventChannel")
  fun userEventSender(pubSubTemplate: PubSubTemplate): MessageHandler {
    val adapter = PubSubMessageHandler(pubSubTemplate, PubSubModel.USER_EVENT_TOPIC)

    adapter.setSuccessCallback { ackId: String, message: Message<*> ->
      log.info("UserEventの送信に成功 ackId:$ackId")
      log.debug("送信に成功したメッセージ message:$message")
    }

    adapter.setFailureCallback { cause: Throwable, message: Message<*> ->
      log.error("UserEventの送信に失敗", cause)
      log.debug("送信に失敗したメッセージ message:$message")
    }

    return adapter
  }
}
