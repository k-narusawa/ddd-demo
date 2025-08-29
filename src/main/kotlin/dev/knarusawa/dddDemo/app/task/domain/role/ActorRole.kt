package dev.knarusawa.dddDemo.app.task.domain.role

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessEvent
import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
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
@Table(name = "ddd_actor_role")
class ActorRole private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("actor_role_id"))
  val actorRoleId: ActorRoleId,
  @Embedded
  @AttributeOverride(name = "value", column = Column("actor_id"))
  val actorId: ActorId,
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
      actorId: ActorId,
      role: Role,
      teamId: TeamId,
    ) = ActorRole(
      actorRoleId = ActorRoleId.new(),
      actorId = actorId,
      teamId = teamId,
      role = role,
    )
  }

  fun role() = this.role
}
