package dev.knarusawa.dddDemo.app.project.domain.task

import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
data class FromTime(
  private val value: LocalDateTime,
) {
  companion object {
    fun of(value: LocalDateTime) = FromTime(value = value)
  }

  fun get() = value

  override fun toString(): String = value.toString()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is FromTime) return false

    if (other.get() != value) return false

    return true
  }

  override fun hashCode(): Int = value.hashCode()
}
