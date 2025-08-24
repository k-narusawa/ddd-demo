package dev.k_narusawa.ddd_demo.app.task.domain.taskEvent

import dev.k_narusawa.ddd_demo.app.identity_access.domain.IdentityAccessDomainEvent
import dev.k_narusawa.ddd_demo.app.task.domain.actor.ActorId
import dev.k_narusawa.ddd_demo.app.task.domain.task.TaskId
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
@Table(name = "ddd_task_event")
class TaskEvent private constructor(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column("task_event_id"))
    val taskEventId: TaskEventId,
    @Embedded
    @AttributeOverride(name = "value", column = Column("task_id"))
    private val taskId: TaskId,
    @Enumerated(EnumType.STRING)
    @AttributeOverride(name = "value", column = Column("task_action"))
    private val taskAction: TaskAction,
    @Embedded
    @AttributeOverride(name = "value", column = Column("occurred_by"))
    private val occurredBy: ActorId,
    @Embedded
    @AttributeOverride(name = "value", column = Column("occurred_on"))
    private val occurredOn: OccurredOn,
    @Version
    @AttributeOverride(name = "value", column = Column("version"))
    private val version: Long? = null,
    @Transient
    private val events: MutableList<IdentityAccessDomainEvent> = mutableListOf(),
)
