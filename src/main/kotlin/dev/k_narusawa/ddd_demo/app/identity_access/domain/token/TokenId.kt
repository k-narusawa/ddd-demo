package dev.k_narusawa.ddd_demo.app.identity_access.domain.token

import jakarta.persistence.Embeddable
import java.util.*

@Embeddable
data class TokenId private constructor(
    private val value: String,
) {
    companion object {
        fun generate() = TokenId(value = UUID.randomUUID().toString())

        fun from(value: String) = TokenId(value = value)
    }

    fun get() = value
}
