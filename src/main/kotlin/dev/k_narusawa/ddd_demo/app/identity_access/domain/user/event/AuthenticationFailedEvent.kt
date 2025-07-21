package dev.k_narusawa.ddd_demo.app.identity_access.domain.user.event

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.User
import org.springframework.context.ApplicationEvent

data class AuthenticationFailedEvent(val user: User) : ApplicationEvent(user)