package dev.k_narusawa.ddd_demo.app.identity_access.domain.activityLog

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
data class ActivityLogId private constructor(
    private val value: String,
) : Serializable {
    companion object {
        fun new(): ActivityLogId = ActivityLogId(value = UUID.randomUUID().toString())
    }

    fun get() = value

    override fun toString() = value
}
