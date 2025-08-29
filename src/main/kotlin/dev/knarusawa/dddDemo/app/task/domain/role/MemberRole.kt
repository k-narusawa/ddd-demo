package dev.knarusawa.dddDemo.app.task.domain.role

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessEvent
import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@Table(name = "ddd_member_role")
class MemberRole private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("member_role_id"))
  val memberRoleId: MemberRoleId,
  @Embedded
  @AttributeOverride(name = "value", column = Column("member_id"))
  val memberId: MemberId,
  @Embedded
  @AttributeOverride(name = "value", column = Column("team_id"))
  val teamId: TeamId,
  @Enumerated(EnumType.STRING)
  @AttributeOverride(name = "value", column = Column("role"))
  private var role: Role,
  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,
  @Transient
  private val events: MutableList<IdentityAccessEvent> = mutableListOf(),
) {
  companion object {
    fun of(
      memberId: MemberId,
      role: Role,
      teamId: TeamId,
    ) = MemberRole(
      memberRoleId = MemberRoleId.new(),
      memberId = memberId,
      teamId = teamId,
      role = role,
    )
  }

  fun role() = this.role
}
