package dev.knarusawa.dddDemo.app.task.adapter.gateway.kurrentdb

import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.executionListener.DatabaseCleanupListener
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import java.time.LocalDateTime

@SpringBootTest
@TestExecutionListeners(listeners = [DatabaseCleanupListener::class])
@DisplayName("アダプター_タスクイベントストア")
class TaskEventStoreRepositoryImplTest
  @Autowired
  constructor(
    private val sut: TaskEventStoreRepositoryImpl,
  ) {
    @Nested
    inner class CommitTest {
      @DisplayName("イベントがコミットできること")
      @Test
      fun event_can_be_committed() {
        val event =
          TaskCreated.of(
            taskId = TaskId.new(),
            eventVersion = 0,
            title = Title.of("test"),
            description = Description.of("test"),
            assignee = ActorId.new(),
            fromTime = FromTime.of(LocalDateTime.now()),
            toTime = ToTime.of(LocalDateTime.now()),
          )
        sut.commit(event)
      }
    }

    @Nested
    inner class EventLoadTest {
      @DisplayName("イベントがロードできること")
      @Test
      fun event_can_load() {
        val taskId = TaskId.new()
        val event =
          TaskCreated.of(
            taskId = taskId,
            eventVersion = 0,
            title = Title.of("test"),
            description = Description.of("test"),
            assignee = ActorId.new(),
            fromTime = FromTime.of(LocalDateTime.now()),
            toTime = ToTime.of(LocalDateTime.now()),
          )
        sut.commit(event)

        val events = sut.loadEvent(taskId = taskId)
        assert(events.size == 1)
      }
    }
  }
