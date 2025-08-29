package dev.knarusawa.dddDemo.app.task.adapter.gateway.kurrentdb

import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId
import dev.knarusawa.dddDemo.executionListener.DatabaseCleanupListener
import io.kurrent.dbclient.KurrentDBPersistentSubscriptionsClient
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
    private val client: KurrentDBPersistentSubscriptionsClient,
    private val sut: TaskEventStoreRepositoryImpl,
  ) {
    @Nested
    inner class CommitTest {
      @DisplayName("イベントがコミットできること")
      @Test
      fun event_can_be_committed() {
        val event =
          TaskCreated.of(
            teamId = TeamId.new(),
            operator = MemberId.new(),
            assigner = MemberId.new(),
            title = Title.of("test"),
            description = Description.of("test"),
            assignee = MemberId.new(),
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
        val event =
          TaskCreated.of(
            teamId = TeamId.new(),
            operator = MemberId.new(),
            assigner = MemberId.new(),
            title = Title.of("test"),
            description = Description.of("test"),
            assignee = MemberId.new(),
            fromTime = FromTime.of(LocalDateTime.now()),
            toTime = ToTime.of(LocalDateTime.now()),
          )
        sut.commit(event)

        val events = sut.loadEvents(taskId = event.taskId)
        assert(events.size == 1)
      }
    }
  }
