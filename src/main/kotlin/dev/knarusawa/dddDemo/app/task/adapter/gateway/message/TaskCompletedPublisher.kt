package dev.knarusawa.dddDemo.app.task.adapter.gateway.message

import org.springframework.integration.annotation.Gateway
import org.springframework.integration.annotation.MessagingGateway

@MessagingGateway
interface TaskCompletedPublisher {
  @Gateway(requestChannel = "taskCompletedEventChannel")
  fun send(payload: String)
}
