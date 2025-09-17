package dev.knarusawa.dddDemo.app.project.domain.member

import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
data class MemberId(
  private val value: String,
) {
  companion object {
    fun new(): MemberId = MemberId(value = UUID.randomUUID().toString())

    fun from(value: String) = MemberId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
