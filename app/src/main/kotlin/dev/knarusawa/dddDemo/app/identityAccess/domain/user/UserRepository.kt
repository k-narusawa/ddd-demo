package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, UserId> {
  fun save(user: User)

  fun findByUsername(username: Username): User?

  fun findByUserId(userId: UserId): User?
}
