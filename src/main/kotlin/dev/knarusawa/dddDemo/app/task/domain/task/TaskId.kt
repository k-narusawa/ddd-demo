package dev.knarusawa.dddDemo.app.task.domain.task

import java.util.UUID

data class TaskId(
  private val value: String,
) {
  companion object {
    fun new(): TaskId = TaskId(value = UUID.randomUUID().toString())

    fun from(value: String) = TaskId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
