package dev.knarusawa.dddDemo.app.task.adapter.gateway.kurrentdb

import com.fasterxml.jackson.databind.ObjectMapper
import dev.knarusawa.dddDemo.app.task.adapter.gateway.kurrentdb.eventData.TaskEventData
import dev.knarusawa.dddDemo.app.task.adapter.gateway.postgresql.TaskDtoRepository
import dev.knarusawa.dddDemo.app.task.application.dto.TaskDto
import dev.knarusawa.dddDemo.app.task.application.eventHandler.TaskProjection
import dev.knarusawa.dddDemo.app.task.domain.task.Task
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEventStoreRepository
import dev.knarusawa.dddDemo.util.logger
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
  private val taskDtoRepository: TaskDtoRepository,
  private val taskEventStoreRepository: TaskEventStoreRepository,
  private val objectMapper: ObjectMapper,
) : TaskProjection {
  companion object {
    private val log = logger()
  }

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
            log.info(
              "タスクイベントを受信 event_id: ${event.originalEvent.eventId}, streamId: ${event.originalEvent.streamId}",
            )
            val taskEvent =
              objectMapper.readValue(event.originalEvent.eventData, TaskEventData::class.java)
            val taskEvents =
              taskEventStoreRepository.loadEvents(taskId = TaskId.from(value = taskEvent.taskId))
            val task = Task.applyFromFirstEvent(events = taskEvents)
            val dto = TaskDto.from(task = task)
            taskDtoRepository.save(dto = dto)
            subscription.ack(event)
          } catch (ex: Exception) {
            log.error("タスクイベントの処理に失敗", ex)
            subscription.nack(NackAction.Park, ex.message, event)
          }
        }
      },
    )
  }
}
