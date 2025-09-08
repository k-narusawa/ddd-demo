package dev.knarusawa.dddDemo.app.project.domain.role

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessEvent
import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
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
  @AttributeOverride(name = "value", column = Column("project_id"))
  val projectId: ProjectId,
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
      projectId: ProjectId,
    ) = MemberRole(
      memberRoleId = MemberRoleId.new(),
      memberId = memberId,
      projectId = projectId,
      role = role,
    )
  }

  fun role() = this.role
}
