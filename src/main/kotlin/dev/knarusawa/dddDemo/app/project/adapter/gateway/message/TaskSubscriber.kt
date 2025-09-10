package dev.knarusawa.dddDemo.app.project.adapter.gateway.message

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders
import dev.knarusawa.dddDemo.app.project.application.port.ReceiveMessageInputBoundary
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskCompleted
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.project.domain.task.event.TaskEvent
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

  @ServiceActivator(inputChannel = "taskEventSubscriptionChannel")
  fun taskEventReceiver(
    payload: String?,
    @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) message: BasicAcknowledgeablePubsubMessage,
  ) {
    handleEvent(payload = payload!!, message = message)
  }

  private fun handleEvent(
    payload: String,
    message: BasicAcknowledgeablePubsubMessage,
  ) {
    val requestId = UUID.randomUUID().toString()
    MDC.put("requestId", requestId)
    log.info("タスクサブスクライバーでイベントを受信 Payload: $payload")

    try {
      when (val taskEvent = TaskEvent.fromPayload(payload = payload)) {
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
      MDC.clear()
      log.info("タスクサブスクライバーでメッセージの処理が完了")
    }
  }
}
