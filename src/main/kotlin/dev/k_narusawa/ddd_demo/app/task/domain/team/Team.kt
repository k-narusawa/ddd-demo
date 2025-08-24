package dev.k_narusawa.ddd_demo.app.task.domain.team

import dev.k_narusawa.ddd_demo.app.identity_access.domain.IdentityAccessDomainEvent
import dev.k_narusawa.ddd_demo.app.task.domain.actor.ActorId
import dev.k_narusawa.ddd_demo.app.task.domain.role.ActorRole
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
    private var members: MutableList<ActorRole>,
    @Version
    @AttributeOverride(name = "value", column = Column("version"))
    private val version: Long? = null,
    @Transient
    private val events: MutableList<IdentityAccessDomainEvent> = mutableListOf(),
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
                    members = mutableListOf(),
                )

            val admin = ActorRole.signedUpFrom(actorId = actorId, teamId = team.teamId)
            team.add(actorRole = admin)

            return team
        }
    }

    fun getTeamName() = this.teamName

    fun getEvents() = this.events.toList()

    fun add(actorRole: ActorRole) {
        this.members.add(actorRole)
    }
}
