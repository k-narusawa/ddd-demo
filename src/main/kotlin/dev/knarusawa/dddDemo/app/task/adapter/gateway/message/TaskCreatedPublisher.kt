package dev.knarusawa.dddDemo.app.task.adapter.gateway.message

import org.springframework.integration.annotation.Gateway
import org.springframework.integration.annotation.MessagingGateway

@MessagingGateway
interface TaskCreatedPublisher {
  @Gateway(requestChannel = "taskCreatedEventChannel")
  fun send(payload: String)
}
