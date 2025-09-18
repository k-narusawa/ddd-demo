package dev.knarusawa.dddDemo.app.identityAccess.adapter.gateway.message

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders
import dev.knarusawa.dddDemo.app.identityAccess.adapter.gateway.db.outbox.IdentityAccessOutboxRepository
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UserEvent
import dev.knarusawa.dddDemo.infrastructure.RequestId
import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.UserEventMessage
import dev.knarusawa.dddDemo.util.logger
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component(value = "identityAccessUserEventSubscriber")
class UserEventSubscriber(
  private val outboxRepository: IdentityAccessOutboxRepository,
) {
  companion object {
    private val log = logger()
  }

  @ServiceActivator(inputChannel = "userEventSubscriptionChannel")
  fun handle(
    @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) message: BasicAcknowledgeablePubsubMessage,
  ) {
    handleEvent(message = message)
  }

  private fun handleEvent(message: BasicAcknowledgeablePubsubMessage) {
    RequestId.set()
    val messageId = message.pubsubMessage.messageId
    log.info("UserEventを受信 messageId: $messageId")

    try {
      val eventMessage = UserEventMessage.parseFrom(message.pubsubMessage.data)
      val event = UserEvent.from(pl = eventMessage)
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
