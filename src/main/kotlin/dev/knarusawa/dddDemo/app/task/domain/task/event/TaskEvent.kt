package dev.knarusawa.dddDemo.app.task.domain.task.event

import dev.knarusawa.dddDemo.app.task.domain.TaskEventType
import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.util.JsonUtil
import java.time.LocalDateTime

sealed class TaskEvent {
  abstract val taskId: TaskId
  abstract val type: TaskEventType
  abstract val projectId: ProjectId
  abstract val operator: MemberId
  abstract val title: Title
  abstract val description: Description?
  abstract val assigner: MemberId?
  abstract val assignee: MemberId?
  abstract val fromTime: FromTime?
  abstract val toTime: ToTime?
  abstract val occurredAt: LocalDateTime
  abstract val completed: Boolean
  abstract val version: Long

  companion object {
    fun fromPayload(
      eventType: TaskEventType,
      payload: String,
    ): TaskEvent =
      when (eventType) {
        TaskEventType.TASK_CREATED -> {
          val jsonMap = JsonUtil.jsonToMap(jsonString = payload)
          TaskCreated(
            taskId = TaskId(jsonMap["taskId"] as String),
            type = TaskEventType.TASK_CREATED,
            projectId = ProjectId(jsonMap["projectId"] as String),
            operator = MemberId(jsonMap["operator"] as String),
            title = Title.of(jsonMap["title"] as String),
            description = Description.of(jsonMap["description"] as String),
            assigner = MemberId.from(jsonMap["assigner"] as String),
            assignee = MemberId.from(jsonMap["assignee"] as String),
            fromTime = FromTime.of(jsonMap["fromTime"] as LocalDateTime),
            toTime = ToTime.of(jsonMap["toTime"] as LocalDateTime),
            occurredAt = LocalDateTime.parse(jsonMap["occurredAt"] as String),
            completed = jsonMap["completed"] as Boolean,
            version = jsonMap["version"] as Long,
          )
        }

        else -> throw IllegalStateException()
      }
  }

  fun toPayload(): String =
    """
    {
      "taskId": "${this.taskId.get()}",
      "projectId": "${this.projectId.get()}",
      "operator": "${this.operator.get()}",
      "title": "${this.title.get()}",
      "description": "${this.description?.get()}",
      "assigner": "${this.assigner?.get()}",
      "assignee": "${this.assignee?.get()}",
      "fromTime": "${this.fromTime?.get()}",
      "toTime": "${this.toTime?.get()}",
      "occurredAt": "${this.occurredAt}",
      "completed": "${this.completed}",
      "version": "${this.version}"
    }
    """.trimIndent()
}
