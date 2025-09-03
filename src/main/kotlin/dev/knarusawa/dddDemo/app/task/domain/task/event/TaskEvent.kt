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
  abstract val taskEventId: TaskEventId
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

  companion object {
    fun fromPayload(payload: String): TaskEvent {
      val jsonMap = JsonUtil.jsonToMap(jsonString = payload)
      val type = TaskEventType.valueOf(jsonMap["type"] as String)
      return when (type) {
        TaskEventType.TASK_CREATED ->
          TaskCreated(
            taskEventId = TaskEventId(jsonMap["taskEventId"] as String),
            taskId = TaskId(jsonMap["taskId"] as String),
            type = TaskEventType.TASK_CREATED,
            projectId = ProjectId(jsonMap["projectId"] as String),
            operator = MemberId(jsonMap["operator"] as String),
            title = Title.of(jsonMap["title"] as String),
            description = (jsonMap["description"] as? String)?.let { Description.of(it) },
            assigner = (jsonMap["assigner"] as? String)?.let { MemberId.from(it) },
            assignee = (jsonMap["assignee"] as? String)?.let { MemberId.from(it) },
            fromTime = (jsonMap["fromTime"] as? LocalDateTime)?.let { FromTime.of(it) },
            toTime = (jsonMap["toTime"] as? LocalDateTime)?.let { ToTime.of(it) },
            occurredAt = LocalDateTime.parse(jsonMap["occurredAt"] as String),
            completed = jsonMap["completed"] as Boolean,
          )

        TaskEventType.TASK_CHANGED ->
          TaskChanged(
            taskEventId = TaskEventId(jsonMap["taskEventId"] as String),
            taskId = TaskId(jsonMap["taskId"] as String),
            type = TaskEventType.TASK_CHANGED,
            projectId = ProjectId(jsonMap["projectId"] as String),
            operator = MemberId(jsonMap["operator"] as String),
            title = Title.of(jsonMap["title"] as String),
            description = (jsonMap["description"] as? String)?.let { Description.of(it) },
            assigner = (jsonMap["assigner"] as? String)?.let { MemberId.from(it) },
            assignee = (jsonMap["assignee"] as? String)?.let { MemberId.from(it) },
            fromTime = (jsonMap["fromTime"] as? LocalDateTime)?.let { FromTime.of(it) },
            toTime = (jsonMap["toTime"] as? LocalDateTime)?.let { ToTime.of(it) },
            occurredAt = LocalDateTime.parse(jsonMap["occurredAt"] as String),
            completed = jsonMap["completed"] as Boolean,
          )

        TaskEventType.TASK_COMPLETED ->
          TaskCompleted(
            taskEventId = TaskEventId(jsonMap["taskEventId"] as String),
            taskId = TaskId(jsonMap["taskId"] as String),
            type = TaskEventType.TASK_COMPLETED,
            projectId = ProjectId(jsonMap["projectId"] as String),
            operator = MemberId(jsonMap["operator"] as String),
            title = Title.of(jsonMap["title"] as String),
            description = (jsonMap["description"] as? String)?.let { Description.of(it) },
            assigner = (jsonMap["assigner"] as? String)?.let { MemberId.from(it) },
            assignee = (jsonMap["assignee"] as? String)?.let { MemberId.from(it) },
            fromTime = (jsonMap["fromTime"] as? LocalDateTime)?.let { FromTime.of(it) },
            toTime = (jsonMap["toTime"] as? LocalDateTime)?.let { ToTime.of(it) },
            occurredAt = LocalDateTime.parse(jsonMap["occurredAt"] as String),
            completed = jsonMap["completed"] as Boolean,
          )
      }
    }
  }

  fun toPayload(): String =
    """
    {
      "taskEventId": "${this.taskEventId.get()}",
      "taskId": "${this.taskId.get()}",
      "type": "${this.type.name}",
      "projectId": "${this.projectId.get()}",
      "operator": "${this.operator.get()}",
      "title": "${this.title.get()}",
      "description": "${this.description?.get()}",
      "assigner": "${this.assigner?.get()}",
      "assignee": "${this.assignee?.get()}",
      "fromTime": "${this.fromTime?.get()}",
      "toTime": "${this.toTime?.get()}",
      "occurredAt": "${this.occurredAt}",
      "completed": ${this.completed}
    }
    """.trimIndent()
}
