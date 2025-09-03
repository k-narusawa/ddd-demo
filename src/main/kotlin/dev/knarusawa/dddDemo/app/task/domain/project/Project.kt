package dev.knarusawa.dddDemo.app.task.domain.project

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
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@Table(name = "ddd_project")
class Project private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("project_id"))
  val projectId: ProjectId,
  @Embedded
  @AttributeOverride(name = "value", column = Column("project_name"))
  private var projectName: ProjectName,
  @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
  @JoinColumn(name = "project_id", referencedColumnName = "project_id")
  private var members: MutableList<MemberRole> = mutableListOf(),
  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,
  @Transient
  private val events: MutableList<IdentityAccessEvent> = mutableListOf(),
) {
  companion object {
    fun of(
      projectName: ProjectName,
      memberId: MemberId,
    ): Project {
      val project =
        Project(
          projectId = ProjectId.new(),
          projectName = projectName,
        )
      project.add(memberId = memberId, role = Role.ADMIN)

      return project
    }
  }

  fun getProjectName() = this.projectName

  fun getEvents() = this.events.toList()

  fun add(
    memberId: MemberId,
    role: Role,
  ) {
    this.members.add(MemberRole.of(memberId = memberId, role = role, projectId = this.projectId))
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
