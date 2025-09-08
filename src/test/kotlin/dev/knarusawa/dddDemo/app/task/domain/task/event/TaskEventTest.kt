package dev.knarusawa.dddDemo.app.task.domain.task.event

import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TaskEventTest {
  @Test
  fun `TaskCreated fromPayload`() {
    val eventType = TaskEventType.TASK_CREATED
    val payload =
      """
      {
        "title": "テスト",
        "taskId": "28b22f77-147b-4435-bbc5-916ae85b789a",
        "toTime": null,
        "version": 1,
        "assignee": null,
        "assigner": null,
        "fromTime": null,
        "operator": "8d669463-ba51-48d6-9121-d0e8952f8131",
        "completed": false,
        "projectId": "bfb8b571-12f6-498f-b839-e38cde4cb833",
        "occurredAt": "2025-09-01T15:30:48.559617",
        "description": "説明"
      }
      """.trimIndent()

    val sut = TaskEvent.fromPayload(payload)

    assertEquals(TaskId("28b22f77-147b-4435-bbc5-916ae85b789a"), sut.taskId)
    assertEquals(TaskEventType.TASK_CREATED, sut.type)
    assertEquals(ProjectId("bfb8b571-12f6-498f-b839-e38cde4cb833"), sut.projectId)
    assertEquals(MemberId("8d669463-ba51-48d6-9121-d0e8952f8131"), sut.operator)
    assertEquals(Title.of("テスト"), sut.title)
    assertEquals(Description.of("説明"), sut.description)
    assertNull(sut.assigner)
    assertNull(sut.assignee)
    assertNull(sut.fromTime)
    assertNull(sut.toTime)
    assertEquals(LocalDateTime.of(2025, 9, 1, 15, 30, 48, 559617000), sut.occurredAt)
    assertEquals(false, sut.completed)
  }
}
