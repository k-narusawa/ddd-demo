package dev.k_narusawa.ddd_demo.app.task.domain.team

import dev.k_narusawa.ddd_demo.app.identity_access.domain.DomainEvent
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
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

  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,

  @Transient
  private val events: MutableList<DomainEvent> = mutableListOf()
) {
  companion object {
    fun of(teamName: TeamName): Team {
      return Team(
        teamId = TeamId.new(),
        teamName = teamName,
      )
    }
  }

  fun getTeamName() = this.teamName
  fun getEvents() = this.events.toList()
}
