package dev.knarusawa.dddDemo.app.project.adapter.gateway.message

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders
import dev.knarusawa.dddDemo.app.project.application.port.UserEventInputBoundary
import dev.knarusawa.dddDemo.infrastructure.RequestId
import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.PLUserEvent
import dev.knarusawa.dddDemo.util.logger
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component(value = "projectUserEventSubscriber")
class UserEventSubscriber(
  private val userEventInputBoundary: UserEventInputBoundary,
) {
  companion object {
    private val log = logger()
  }

  @ServiceActivator(inputChannel = "projectUserEventSubscriptionChannel")
  fun handle(
    @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) message: BasicAcknowledgeablePubsubMessage,
  ) {
    RequestId.set()
    val messageId = message.pubsubMessage.messageId
    log.info("UserEventを受信 messageId: $messageId")

    try {
      val pl = PLUserEvent.parseFrom(message.pubsubMessage.data)
      userEventInputBoundary.handle(pl = pl)
      message.ack()
    } catch (e: Exception) {
      log.error("メッセージの処理に失敗", e)
      message.nack()
    } finally {
      log.info("メッセージの処理が完了")
      RequestId.clear()
    }
  }
}
