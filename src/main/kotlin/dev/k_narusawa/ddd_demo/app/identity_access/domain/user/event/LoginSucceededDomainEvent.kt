package dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event

import dev.k_narusawa.ddd_demo.app.identity_access.domain.IdentityAccessDomainEvent
import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User

data class LoginSucceededDomainEvent(
    val user: User,
    val userAgent: String,
    val ipAddress: String,
) : IdentityAccessDomainEvent(source = user)
