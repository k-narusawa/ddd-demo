package dev.k_narusawa.ddd_demo.app.identity_access.domain.loginAttempt

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoginAttemptRepository : JpaRepository<LoginAttempt, UserId> {
  fun save(loginAttempt: LoginAttempt)
  fun findByUserId(userId: UserId): LoginAttempt?
}