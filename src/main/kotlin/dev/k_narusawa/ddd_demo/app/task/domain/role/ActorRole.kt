package dev.k_narusawa.ddd_demo.app.task.domain.role

import dev.k_narusawa.ddd_demo.app.identity_access.domain.IdentityAccessDomainEvent
import dev.k_narusawa.ddd_demo.app.task.domain.actor.ActorId
import dev.k_narusawa.ddd_demo.app.task.domain.team.TeamId
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
@Table(name = "ddd_actor_role")
class ActorRole private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("actor_role_id"))
  val actorRoleId: ActorRoleId,

  @Embedded
  @AttributeOverride(name = "value", column = Column("actor_id"))
  private var actorId: ActorId,

  @Embedded
  @AttributeOverride(name = "value", column = Column("team_id"))
  private var teamId: TeamId,

  @Enumerated(EnumType.STRING)
  @AttributeOverride(name = "value", column = Column("role"))
  private var role: Role,

  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,

  @Transient
  private val events: MutableList<IdentityAccessDomainEvent> = mutableListOf()
) {
}
