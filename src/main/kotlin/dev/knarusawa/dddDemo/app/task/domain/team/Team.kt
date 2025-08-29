package dev.knarusawa.dddDemo.app.task.domain.team

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessEvent
import dev.knarusawa.dddDemo.app.task.domain.actor.ActorId
import dev.knarusawa.dddDemo.app.task.domain.role.ActorRole
import dev.knarusawa.dddDemo.app.task.domain.role.Role
import jakarta.persistence.AttributeOverride
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@Table(name = "ddd_team")
class Team private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("team_id"))
  val teamId: TeamId,
  @Embedded
  @AttributeOverride(name = "value", column = Column("team_name"))
  private var teamName: TeamName,
  @OneToMany(cascade = [CascadeType.ALL])
  @JoinColumn(name = "team_id", referencedColumnName = "team_id")
  private var members: MutableList<ActorRole> = mutableListOf(),
  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,
  @Transient
  private val events: MutableList<IdentityAccessEvent> = mutableListOf(),
) {
  companion object {
    fun of(
      teamName: TeamName,
      actorId: ActorId,
    ): Team {
      val team =
        Team(
          teamId = TeamId.new(),
          teamName = teamName,
        )
      team.add(actorId = actorId, role = Role.ADMIN)

      return team
    }
  }

  fun getTeamName() = this.teamName

  fun getEvents() = this.events.toList()

  fun add(
    actorId: ActorId,
    role: Role,
  ) {
    this.members.add(ActorRole.of(actorId = actorId, role = role, teamId = this.teamId))
  }

  fun hasWriteRole(member: ActorId): Boolean {
    return this.members.any { actorRole ->
      return if (actorRole.actorId == member) {
        actorRole.role() == Role.ADMIN || actorRole.role() == Role.WRITE
      } else {
        false
      }
    }
  }
}
