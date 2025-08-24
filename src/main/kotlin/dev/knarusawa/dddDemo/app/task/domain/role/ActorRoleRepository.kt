package dev.knarusawa.dddDemo.app.task.domain.role

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActorRoleRepository : JpaRepository<ActorRole, ActorRoleId>
