package dev.k_narusawa.ddd_demo.app.task.domain.taskEvent

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
data class TaskEventId(
    private val value: String,
) : Serializable {
    companion object {
        fun new(): TaskEventId = TaskEventId(value = UUID.randomUUID().toString())

        fun from(value: String) = TaskEventId(value = value)
    }

    fun get() = value

    override fun toString() = value
}
