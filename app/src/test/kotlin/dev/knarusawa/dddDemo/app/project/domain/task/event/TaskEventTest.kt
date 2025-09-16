package dev.knarusawa.dddDemo.app.project.domain.task.event

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.task.Description
import dev.knarusawa.dddDemo.app.project.domain.task.FromTime
import dev.knarusawa.dddDemo.app.project.domain.task.TaskId
import dev.knarusawa.dddDemo.app.project.domain.task.Title
import dev.knarusawa.dddDemo.util.ProtobufUtil
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import java.time.LocalDateTime
import kotlin.test.assertEquals

@DisplayName("イベント_タスクイベント")
class TaskEventTest {
  @Test
  @DisplayName("protobufの形式に変換できること")
  fun can_convert_to_protobuf() {
    val sut =
      TaskCreated.of(
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

    val actual = sut.toEventMessage()

    assertEquals(sut.taskId.get(), actual.taskId)
    assertEquals(sut.projectId.get(), actual.projectId)
    assertEquals(sut.operator.get(), actual.operatorId)
    assertEquals("テスト", actual.title)
    assertEquals("テスト", actual.description)
    assertEquals(sut.assignee?.get(), actual.assigneeId)
    assertEquals("", actual.assignerId)
    assertEquals(sut.fromTime?.get(), ProtobufUtil.toLocalDateTime(actual.fromTime))
    assertNull(ProtobufUtil.toLocalDateTime(actual.toTime))
  }

  @Test
  @DisplayName("protobufの形式から変換できること")
  fun can_convert_from_protobuf() {
    val event =
      TaskCreated.of(
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
    val eventMessage = event.toEventMessage().toByteArray()

    val sut = TaskEvent.fromEventMessage(ba = eventMessage)

    assertEquals(event.eventId, sut.eventId)
    assertEquals(event.taskId, sut.taskId)
    assertEquals(event.operator, sut.operator)
    assertEquals(event.title, sut.title)
    assertEquals(event.description, sut.description)
    assertEquals(event.assignee, sut.assignee)
    assertEquals(event.assigner, sut.assigner)
    assertEquals(event.fromTime, sut.fromTime)
    assertEquals(event.toTime, sut.toTime)
    assertEquals(event.occurredAt, sut.occurredAt)
  }
}
