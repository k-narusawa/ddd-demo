package dev.knarusawa.dddDemo.app.task.domain.role

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
data class MemberRoleId(
  private val value: String,
) : Serializable {
  companion object {
    fun new(): MemberRoleId = MemberRoleId(value = UUID.randomUUID().toString())

    fun from(value: String) = MemberRoleId(value = value)
  }

  fun get() = value

  override fun toString() = value
}
