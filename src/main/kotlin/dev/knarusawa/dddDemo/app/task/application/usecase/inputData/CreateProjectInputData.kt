package dev.knarusawa.dddDemo.app.task.application.usecase.inputData

import dev.knarusawa.dddDemo.app.task.domain.member.MemberId
import dev.knarusawa.dddDemo.app.task.domain.project.ProjectName

data class CreateProjectInputData(
  val memberId: MemberId,
  val projectName: ProjectName,
) {
  companion object {
    fun of(
      memberId: String,
      projectName: String,
    ) = CreateProjectInputData(
      memberId = MemberId.Companion.from(value = memberId),
      projectName = ProjectName.Companion.of(value = projectName),
    )
  }
}
