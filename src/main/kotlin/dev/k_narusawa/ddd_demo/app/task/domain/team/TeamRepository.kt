package dev.k_narusawa.ddd_demo.app.task.domain.team

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository : JpaRepository<Team, TeamId> {
  fun save(team: Team)
}
