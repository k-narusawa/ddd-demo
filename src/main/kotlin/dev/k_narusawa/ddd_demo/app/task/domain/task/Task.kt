package dev.k_narusawa.ddd_demo.app.task.domain.task

import dev.k_narusawa.ddd_demo.app.identity_access.domain.IdentityAccessDomainEvent
import dev.k_narusawa.ddd_demo.app.task.domain.actor.ActorId
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@Table(name = "ddd_task")
class Task private constructor(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column("task_id"))
    val taskId: TaskId,
    @Embedded
    @AttributeOverride(name = "value", column = Column("title"))
    private var title: Title,
    @Embedded
    @AttributeOverride(name = "value", column = Column("description"))
    private var description: Description?,
    @Embedded
    @AttributeOverride(name = "value", column = Column("assignee"))
    private val assignee: ActorId?,
    @Embedded
    @AttributeOverride(name = "value", column = Column("from_time"))
    private val fromTime: FromTime?,
    @Embedded
    @AttributeOverride(name = "value", column = Column("to_time"))
    private val toTime: ToTime?,
    @Version
    @AttributeOverride(name = "value", column = Column("version"))
    private val version: Long? = null,
    @Transient
    private val events: MutableList<IdentityAccessDomainEvent> = mutableListOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Task) return false

        if (taskId !== other.taskId) return false

        return true
    }

    override fun hashCode(): Int = taskId.hashCode()
}
