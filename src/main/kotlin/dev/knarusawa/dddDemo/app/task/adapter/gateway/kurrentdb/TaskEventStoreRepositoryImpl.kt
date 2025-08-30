package dev.knarusawa.dddDemo.app.task.adapter.gateway.kurrentdb

import com.fasterxml.jackson.databind.ObjectMapper
import dev.knarusawa.dddDemo.app.task.adapter.gateway.kurrentdb.eventData.TaskEventData
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEvent
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEventStoreRepository
import dev.knarusawa.dddDemo.util.logger
import io.kurrent.dbclient.AppendToStreamOptions
import io.kurrent.dbclient.EventData
import io.kurrent.dbclient.KurrentDBClient
import io.kurrent.dbclient.ReadStreamOptions
import io.kurrent.dbclient.StreamState
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class TaskEventStoreRepositoryImpl(
  private val client: KurrentDBClient,
  private val objectMapper: ObjectMapper,
) : TaskEventStoreRepository {
  companion object {
    private const val STREAM_NAME_PREFIX = "task_event-"
    private val log = logger()
  }

  private fun TaskId.streamName() = "$STREAM_NAME_PREFIX$this"

  override fun commit(event: TaskEvent) {
    log.info("イベントのコミット task_id: ${event.taskId.get()}")
    val eventData =
      EventData
        .builderAsJson(
          UUID.randomUUID(),
          event.type.name,
          objectMapper.writeValueAsBytes(TaskEventData.from(event)),
        ).build()

    val options = AppendToStreamOptions.get().streamState(StreamState.any())
    client.appendToStream(event.taskId.streamName(), options, eventData).get()
  }

  override fun loadEvents(taskId: TaskId): List<TaskEvent> {
    log.info("イベントのロード task_id: ${taskId.get()}")
    val options =
      ReadStreamOptions
        .get()
        .forwards()
        .fromStart()

    val result = client.readStream(taskId.streamName(), options).get()
    val eventDataList =
      result.events.map { event ->
        objectMapper.readValue(event.originalEvent.eventData, TaskEventData::class.java)
      }

    return eventDataList.map { eventData -> eventData.to() }
  }
}
