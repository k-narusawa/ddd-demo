package dev.k_narusawa.ddd_demo.app.identity_access.domain.activityLog

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityLogRepository : JpaRepository<ActivityLog, ActivityLogId> {
  fun save(log: ActivityLog)
}