package dev.k_narusawa.ddd_demo.app.task.domain.task

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
data class TaskId(
  private val value: String
) : Serializable {
  companion object {
    fun new(): TaskId {
      return TaskId(value = UUID.randomUUID().toString())
    }

    fun from(value: String) = TaskId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
