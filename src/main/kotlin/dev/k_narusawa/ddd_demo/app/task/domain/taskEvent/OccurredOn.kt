package dev.k_narusawa.ddd_demo.app.task.domain.taskEvent

import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
data class OccurredOn private constructor(
    private val value: LocalDateTime,
) {
    companion object {
        fun of(value: LocalDateTime) = OccurredOn(value = value)
    }

    fun get() = value

    override fun toString(): String = value.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OccurredOn) return false

        if (other.get() != value) return false

        return true
    }

    override fun hashCode(): Int = value.hashCode()
}
