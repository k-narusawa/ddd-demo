package dev.knarusawa.dddDemo.app.task.adapter.gateway.kurrentdb

import dev.knarusawa.dddDemo.app.task.application.eventHandler.TaskProjection
import io.kurrent.dbclient.KurrentDBPersistentSubscriptionsClient
import io.kurrent.dbclient.NackAction
import io.kurrent.dbclient.PersistentSubscription
import io.kurrent.dbclient.PersistentSubscriptionListener
import io.kurrent.dbclient.ResolvedEvent
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class TaskProjectionImpl(
  @Qualifier("taskProjection")
  private val client: KurrentDBPersistentSubscriptionsClient,
) : TaskProjection {
  @PostConstruct
  override fun handle() {
    client.subscribeToAll(
      "TaskProjectionGroup",
      object : PersistentSubscriptionListener() {
        override fun onEvent(
          subscription: PersistentSubscription,
          retryCount: Int,
          event: ResolvedEvent,
        ) {
          try {
            println("イベントを受信しました")
            println("eventId: ${event.originalEvent.eventId}")
            println("eventType: ${event.originalEvent.eventType}")
            println("streamId: ${event.originalEvent.streamId}")
            println("${event.position}")
            subscription.ack(event)
          } catch (ex: Exception) {
            subscription.nack(NackAction.Park, ex.message, event)
          }
        }
      },
    )
  }
}
