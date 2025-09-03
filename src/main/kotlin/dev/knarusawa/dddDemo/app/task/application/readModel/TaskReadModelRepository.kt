package dev.knarusawa.dddDemo.app.task.application.readModel

import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.task.Description
import dev.knarusawa.dddDemo.app.task.domain.task.FromTime
import dev.knarusawa.dddDemo.app.task.domain.task.TaskId
import dev.knarusawa.dddDemo.app.task.domain.task.Title
import dev.knarusawa.dddDemo.app.task.domain.task.ToTime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TaskReadModelRepository : JpaRepository<TaskReadModel, TaskId> {
  fun save(task: TaskReadModel)

  @Modifying
  @Query(
    (
      "UPDATE TaskReadModel t SET " +
        "t.operator = :operator, " +
        "t.title = :title, " +
        "t.description = :description, " +
        "t.assigner = :assigner, " +
        "t.assignee = :assignee, " +
        "t.fromTime = :fromTime, " +
        "t.toTime = :toTime, " +
        "t.completed = :completed " +
        "WHERE t.taskId = :taskId"
    ),
  )
  fun update(
    @Param("taskId") taskId: TaskId?,
    @Param("operator") operator: MemberId,
    @Param("title") title: Title,
    @Param("description") description: Description?,
    @Param("assigner") assigner: MemberId?,
    @Param("assignee") assignee: MemberId?,
    @Param("fromTime") fromTime: FromTime?,
    @Param("toTime") toTime: ToTime?,
    @Param("completed") completed: Boolean?,
  )

  fun findByTaskId(taskId: TaskId): TaskReadModel?
}
