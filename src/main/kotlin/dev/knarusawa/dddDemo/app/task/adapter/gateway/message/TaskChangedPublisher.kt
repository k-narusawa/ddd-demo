package dev.knarusawa.dddDemo.app.task.adapter.gateway.message

import org.springframework.integration.annotation.Gateway
import org.springframework.integration.annotation.MessagingGateway

@MessagingGateway
interface TaskChangedPublisher {
  @Gateway(requestChannel = "taskChangedEventChannel")
  fun send(payload: String)
}
