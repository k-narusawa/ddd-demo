package dev.knarusawa.dddDemo.app.task.adapter.gateway.message

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders
import dev.knarusawa.dddDemo.util.logger
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class TaskSubscriber {
  companion object {
    private val log = logger()
  }

  @ServiceActivator(inputChannel = "taskSubscriptionChannel")
  fun messageReceiver(
    payload: String?,
    @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) message: BasicAcknowledgeablePubsubMessage,
  ) {
    log.info("タスクサブスクリプションでイベントを受信 Payload: $payload")
    message.ack()
  }
}
