package dev.knarusawa.dddDemo.app.task.domain.task

import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
import java.time.LocalDateTime

class TaskState private constructor(
  val taskId: TaskId,
  private var title: Title,
  private var description: Description?,
  private val assignee: ActorId?,
  private val fromTime: FromTime?,
  private val toTime: ToTime?,
  private val occurredAt: LocalDateTime,
)
