package dev.k_narusawa.ddd_demo.app.identity_access.domain.activityLog

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.LocalDateTime

@Entity
@Table(name = "ddd_activity_log")
class ActivityLog private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("activity_log_id"))
  val activityLogId: ActivityLogId,

  @Embedded
  @AttributeOverride(name = "value", column = Column("user_id"))
  private val userId: UserId,

  @Enumerated(EnumType.STRING)
  @AttributeOverride(name = "value", column = Column("action_type"))
  private val actionType: ActionType,

  @AttributeOverride(name = "value", column = Column("ip_address"))
  private val ipAddress: String?,

  @AttributeOverride(name = "value", column = Column("user_agent"))
  private val userAgent: String?,

  @AttributeOverride(name = "value", column = Column("occurred_on"))
  private val occurredOn: LocalDateTime?,

  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,
) {
  companion object {
    fun new(
      userId: UserId,
      actionType: ActionType,
      ipAddress: String,
      userAgent: String,
      occurredOn: LocalDateTime
    ) = ActivityLog(
      activityLogId = ActivityLogId.new(),
      userId = userId,
      actionType = actionType,
      userAgent = userAgent,
      ipAddress = ipAddress,
      occurredOn = occurredOn
    )
  }
}