package dev.k_narusawa.ddd_demo.app.task.domain.actor

import dev.k_narusawa.ddd_demo.app.identity_access.domain.IdentityAccessDomainEvent
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@Table(name = "ddd_actor")
class Actor private constructor(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column("actor_id"))
    val actorId: ActorId,
    @Embedded
    @AttributeOverride(name = "value", column = Column("personal_name"))
    private var personalName: PersonalName,
    @Version
    @AttributeOverride(name = "value", column = Column("version"))
    private val version: Long? = null,
    @Transient
    private val events: MutableList<IdentityAccessDomainEvent> = mutableListOf(),
) {
    companion object {
        fun signup(
            actorId: ActorId,
            personalName: PersonalName,
        ) = Actor(
            actorId = actorId,
            personalName = personalName,
        )
    }

    fun getEvents() = events.toList()

    fun getPersonalName() = personalName
}
