package dev.k_narusawa.ddd_demo.app.task.domain.task

import jakarta.persistence.Embeddable

@Embeddable
data class Description private constructor(
    private val value: String,
) {
    init {
        require(value.isNotBlank()) { "概要は空にできません" }
        require(value.length >= 2) { "概要は2文字以上である必要があります" }
    }

    companion object {
        fun of(value: String) = Description(value)
    }

    fun get() = value

    override fun toString() = "********"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Description) return false

        if (other.get() != value) return false

        return true
    }

    override fun hashCode(): Int = value.hashCode()
}
