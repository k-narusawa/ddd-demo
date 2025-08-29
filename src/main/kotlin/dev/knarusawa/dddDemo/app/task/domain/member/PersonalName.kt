package dev.knarusawa.dddDemo.app.task.domain.actor

import jakarta.persistence.Embeddable

@Embeddable
data class PersonalName(
  private val value: String,
) {
  init {
    require(value.isNotBlank()) { "名前は空にできません" }
    require(value.length >= 2) { "名前は2文字以上である必要があります" }
  }

  companion object {
    fun of(value: String) = PersonalName(value = value)
  }

  fun get() = value

  override fun toString() = "*****"

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is PersonalName) return false

    if (other.get() != value) return false

    return true
  }

  override fun hashCode(): Int = value.hashCode()
}
