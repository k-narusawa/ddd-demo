package dev.knarusawa.dddDemo.app.identityAccess.domain.loginAttempt

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoginAttemptRepository : JpaRepository<LoginAttempt, UserId> {
  fun save(loginAttempt: LoginAttempt)

  fun findByUserId(userId: UserId): LoginAttempt?
}
