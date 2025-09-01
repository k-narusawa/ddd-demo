package dev.knarusawa.dddDemo.app.task.adapter.gateway.message

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders
import dev.knarusawa.dddDemo.app.task.application.port.ReceiveMessageInputBoundary
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCompleted
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEvent
import dev.knarusawa.dddDemo.util.logger
import org.slf4j.MDC
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TaskSubscriber(
  private val receiveMessageInputBoundary: ReceiveMessageInputBoundary,
) {
  companion object {
    private val log = logger()
  }

  @ServiceActivator(inputChannel = "taskSubscriptionChannel")
  fun messageReceiver(
    payload: String?,
    @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) message: BasicAcknowledgeablePubsubMessage,
  ) {
    val requestId = UUID.randomUUID().toString()
    MDC.put("requestId", requestId)
    log.info("タスクサブスクライバーでイベントを受信 Payload: $payload")

    try {
      val event = TaskEvent.fromPayload(payload = payload!!)
      when (event) {
        is TaskCreated ->
          receiveMessageInputBoundary.handle(event = event)

        is TaskChanged ->
          receiveMessageInputBoundary.handle(event = event)

        is TaskCompleted ->
          receiveMessageInputBoundary.handle(event = event)
      }
      message.ack()
    } catch (e: Exception) {
      log.error("タスクサブスクライバーでメッセージの処理に失敗", e)
      message.nack()
    } finally {
      MDC.clear()
    }
  }
}
