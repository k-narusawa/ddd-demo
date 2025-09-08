package dev.knarusawa.dddDemo.app.project.domain.task.event

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.task.Description
import dev.knarusawa.dddDemo.app.project.domain.task.FromTime
import dev.knarusawa.dddDemo.app.project.domain.task.TaskId
import dev.knarusawa.dddDemo.app.project.domain.task.Title
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertIs

@DisplayName("イベント_タスク完了")
class TaskCompletedTest {
  @Test
  @DisplayName("イベントをシリアライズできること")
  fun event_can_deserialize_from_payload() {
    val sut =
      TaskCompleted.of(
        taskId = TaskId.new(),
        projectId = ProjectId.new(),
        operator = MemberId.new(),
        title = Title.of(value = "テスト"),
        description = Description.of(value = "テスト"),
        assigner = null,
        assignee = MemberId.new(),
        fromTime = FromTime.of(value = LocalDateTime.of(2000, 1, 1, 1, 1)),
        toTime = null,
      )

    val payload = sut.toPayload()

    val decodedEvent = TaskEvent.fromPayload(payload)
    assertIs<TaskCompleted>(decodedEvent)
    assertEquals(sut.taskId, decodedEvent.taskId)
    assertEquals(sut.projectId, decodedEvent.projectId)
    assertEquals(sut.operator, decodedEvent.operator)
    assertEquals(sut.title, decodedEvent.title)
    assertEquals(sut.description, decodedEvent.description)
  }
}
