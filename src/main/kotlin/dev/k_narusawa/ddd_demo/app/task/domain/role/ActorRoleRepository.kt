package dev.k_narusawa.ddd_demo.app.task.domain.role

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActorRoleRepository : JpaRepository<ActorRole, ActorRoleId> {
}
