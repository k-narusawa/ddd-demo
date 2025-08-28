package dev.knarusawa.dddDemo.app.task.adapter.gateway.kurrentdb

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEvent
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEventStoreRepository
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
) : TaskEventStoreRepository {
  companion object {
    private val objectMapper = ObjectMapper().registerModule(JavaTimeModule())
  }

  override fun commit(event: TaskEvent) {
    val eventData =
      EventData
        .builderAsJson(
          UUID.randomUUID(),
          event.type.name,
          objectMapper.writeValueAsBytes(event),
        ).build()

    val options = AppendToStreamOptions.get().streamState(StreamState.noStream())

    client.appendToStream(event.taskId.get(), options, eventData).get()
  }

  override fun loadEvent(taskId: TaskId): List<TaskEvent> {
    val options =
      ReadStreamOptions
        .get()
        .forwards()
        .fromStart()

    val result = client.readStream(taskId.get(), options).get()

    return result.events.map { event ->
      objectMapper.readValue(event.getOriginalEvent().getEventData(), TaskEvent::class.java)
    }
  }
}
