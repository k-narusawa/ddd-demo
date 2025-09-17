package dev.knarusawa.dddDemo.app.identityAccess.adapter.gateway.message

import org.springframework.integration.annotation.Gateway
import org.springframework.integration.annotation.MessagingGateway

@MessagingGateway
interface UserEventPublisher {
  @Gateway(requestChannel = "userEventChannel")
  fun publish(message: ByteArray)
}
