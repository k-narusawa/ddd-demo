package dev.knarusawa.dddDemo.app.project.adapter.gateway.message

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders
import dev.knarusawa.dddDemo.app.project.application.port.ReceiveMessageInputBoundary
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskCompleted
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEvent
import dev.knarusawa.dddDemo.infrastructure.RequestId
import dev.knarusawa.dddDemo.publishedLanguage.project.proto.TaskEventMessage
import dev.knarusawa.dddDemo.util.logger
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class TaskEventSubscriber(
  private val receiveMessageInputBoundary: ReceiveMessageInputBoundary,
) {
  companion object {
    private val log = logger()
  }

  @ServiceActivator(inputChannel = "taskEventSubscriptionChannel")
  fun taskEventReceiver(
    @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) message: BasicAcknowledgeablePubsubMessage,
  ) {
    handleEvent(message = message)
  }

  private fun handleEvent(message: BasicAcknowledgeablePubsubMessage) {
    RequestId.set()
    val messageId = message.pubsubMessage.messageId
    log.info("タスクサブスクライバーでイベントを受信 Payload: $messageId")

    try {
      val eventMessage = TaskEventMessage.parseFrom(message.pubsubMessage.data)
      when (val taskEvent = TaskEvent.fromEventMessage(eventMessage = eventMessage)) {
        is TaskCreated ->
          receiveMessageInputBoundary.handle(event = taskEvent)

        is TaskChanged ->
          receiveMessageInputBoundary.handle(event = taskEvent)

        is TaskCompleted ->
          receiveMessageInputBoundary.handle(event = taskEvent)
      }
      message.ack()
    } catch (e: Exception) {
      log.error("タスクサブスクライバーでメッセージの処理に失敗", e)
      message.nack()
    } finally {
      log.info("タスクサブスクライバーでメッセージの処理が完了")
      RequestId.clear()
    }
  }
}
