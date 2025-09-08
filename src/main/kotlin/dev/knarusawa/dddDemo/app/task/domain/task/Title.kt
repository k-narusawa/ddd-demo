package dev.knarusawa.dddDemo.app.task.domain.task

import jakarta.persistence.Embeddable
import kotlinx.serialization.Serializable

@Serializable
@Embeddable
data class Title(
  private val value: String,
) {
  init {
    require(value.isNotBlank()) { "タイトルは空にできません" }
    require(value.length >= 2) { "タイトルは2文字以上である必要があります" }
  }

  companion object {
    fun of(value: String) = Title(value)
  }

  fun get() = value

  override fun toString() = "********"

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Title) return false

    if (other.get() != value) return false

    return true
  }

  override fun hashCode(): Int = value.hashCode()
}
