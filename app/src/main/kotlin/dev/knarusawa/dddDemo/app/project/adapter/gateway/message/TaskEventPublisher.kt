package dev.knarusawa.dddDemo.app.project.adapter.gateway.message

import org.springframework.integration.annotation.Gateway
import org.springframework.integration.annotation.MessagingGateway

@MessagingGateway
interface TaskEventPublisher {
  @Gateway(requestChannel = "taskEventChannel")
  fun publish(message: ByteArray)
}
