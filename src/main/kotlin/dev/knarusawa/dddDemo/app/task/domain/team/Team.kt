package dev.knarusawa.dddDemo.app.task.domain.team

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessEvent
import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.role.MemberRole
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
  private var members: MutableList<MemberRole> = mutableListOf(),
  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,
  @Transient
  private val events: MutableList<IdentityAccessEvent> = mutableListOf(),
) {
  companion object {
    fun of(
      teamName: TeamName,
      memberId: MemberId,
    ): Team {
      val team =
        Team(
          teamId = TeamId.new(),
          teamName = teamName,
        )
      team.add(memberId = memberId, role = Role.ADMIN)

      return team
    }
  }

  fun getTeamName() = this.teamName

  fun getEvents() = this.events.toList()

  fun add(
    memberId: MemberId,
    role: Role,
  ) {
    this.members.add(MemberRole.of(memberId = memberId, role = role, teamId = this.teamId))
  }

  fun hasWriteRole(member: MemberId): Boolean {
    return this.members.any { memberRole ->
      return if (memberRole.memberId == member) {
        memberRole.role() == Role.ADMIN || memberRole.role() == Role.WRITE
      } else {
        false
      }
    }
  }
}
