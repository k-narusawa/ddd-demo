package dev.knarusawa.dddDemo.app.task.application.dto

import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.Task
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import dev.knarusawa.dddDemo.app.task.domain.team.TeamId
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Version

@Table(name = "ddd_task_projection")
@Entity
data class TaskDto(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("task_id"))
  val taskId: TaskId,
  @Embedded
  @AttributeOverride(name = "value", column = Column("team_id"))
  val teamId: TeamId,
  @Embedded
  @AttributeOverride(name = "value", column = Column("operator"))
  val operator: MemberId,
  @Embedded
  @AttributeOverride(name = "value", column = Column("title"))
  val title: Title,
  @Embedded
  @AttributeOverride(name = "value", column = Column("description"))
  val description: Description?,
  @Embedded
  @AttributeOverride(name = "value", column = Column("assigner"))
  val assigner: MemberId?,
  @Embedded
  @AttributeOverride(name = "value", column = Column("assignee"))
  val assignee: MemberId?,
  @Embedded
  @AttributeOverride(name = "value", column = Column("from_time"))
  val fromTime: FromTime?,
  @Embedded
  @AttributeOverride(name = "value", column = Column("to_time"))
  val toTime: ToTime?,
  @AttributeOverride(name = "value", column = Column("completed"))
  val completed: Boolean,
  @Version
  @AttributeOverride(name = "value", column = Column("version"))
  val version: Long,
) {
  companion object {
    fun from(task: Task) =
      TaskDto(
        taskId = task.state.taskId,
        teamId = task.state.teamId,
        operator = task.state.operator,
        title = task.state.title,
        description = task.state.description,
        assigner = task.state.assigner,
        assignee = task.state.assignee,
        fromTime = task.state.fromTime,
        toTime = task.state.toTime,
        completed = task.state.completed,
        version = task.state.version,
      )
  }
}
