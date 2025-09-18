package dev.knarusawa.dddDemo.testFactory

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.Project
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectMember
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectName
import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectEvent
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

object TestProjectFactory {
  fun createProject(
    projectId: ProjectId = ProjectId.new(),
    projectName: ProjectName = ProjectName.of("テストプロジェクト"),
    members: MutableSet<ProjectMember> = mutableSetOf(ProjectMember.adminMember(MemberId.new())),
  ): Project {
    val constructor = Project::class.primaryConstructor!!
    constructor.isAccessible = true
    return constructor.call(
      projectId,
      projectName,
      members,
      mutableListOf<ProjectEvent>(),
    )
  }
}
