package dev.knarusawa.dddDemo.app.project.application.readModel

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.task.Description
import dev.knarusawa.dddDemo.app.project.domain.task.FromTime
import dev.knarusawa.dddDemo.app.project.domain.task.Task
import dev.knarusawa.dddDemo.app.project.domain.task.TaskId
import dev.knarusawa.dddDemo.app.project.domain.task.Title
import dev.knarusawa.dddDemo.app.project.domain.task.ToTime
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "ddd_task_read_model")
@Entity
data class TaskReadModel(
  @EmbeddedId
  @AttributeOverride(name = "value", column = Column("task_id"))
  val taskId: TaskId,
  @Embedded
  @AttributeOverride(name = "value", column = Column("project_id"))
  val projectId: ProjectId,
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
) {
  companion object {
    fun from(task: Task) =
      TaskReadModel(
        taskId = task.state.taskId,
        projectId = task.state.projectId,
        operator = task.state.operator,
        title = task.state.title,
        description = task.state.description,
        assigner = task.state.assigner,
        assignee = task.state.assignee,
        fromTime = task.state.fromTime,
        toTime = task.state.toTime,
        completed = task.state.completed,
      )
  }
}
