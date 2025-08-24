package dev.k_narusawa.ddd_demo.app.task.domain.task

import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
data class ToTime private constructor(
    private val value: LocalDateTime,
) {
    companion object {
        fun of(value: LocalDateTime) = ToTime(value = value)
    }

    fun get() = value

    override fun toString(): String = value.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ToTime) return false

        if (other.get() != value) return false

        return true
    }

    override fun hashCode(): Int = value.hashCode()
}
