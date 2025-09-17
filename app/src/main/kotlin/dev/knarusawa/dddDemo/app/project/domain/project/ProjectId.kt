package dev.knarusawa.dddDemo.app.project.domain.project

import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
data class ProjectId(
  private val value: String,
) {
  companion object {
    fun new(): ProjectId = ProjectId(value = UUID.randomUUID().toString())

    fun from(value: String) = ProjectId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
