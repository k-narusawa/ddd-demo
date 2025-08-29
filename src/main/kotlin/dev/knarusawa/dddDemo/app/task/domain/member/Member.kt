package dev.knarusawa.dddDemo.app.task.domain.member

import dev.knarusawa.dddDemo.app.identityAccess.domain.IdentityAccessEvent
import dev.knarusawa.dddDemo.app.task.domain.actor.PersonalName
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@Table(name = "ddd_member")
class Member private constructor(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("member_id"))
  val memberId: MemberId,
  @Embedded
  @AttributeOverride(name = "value", column = Column("personal_name"))
  private var personalName: PersonalName,
  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  private val version: Long? = null,
  @Transient
  private val events: MutableList<IdentityAccessEvent> = mutableListOf(),
) {
  companion object {
    fun signup(
      memberId: MemberId,
      personalName: PersonalName,
    ) = Member(
      memberId = memberId,
      personalName = personalName,
    )
  }

  fun getEvents() = events.toList()

  fun getPersonalName() = personalName
}
