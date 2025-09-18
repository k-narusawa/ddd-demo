package dev.knarusawa.dddDemo.app.project.domain.project

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.project.command.AddProjectMemberCommand
import dev.knarusawa.dddDemo.app.project.domain.project.command.CreateProjectCommand
import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectCreated
import dev.knarusawa.dddDemo.app.project.domain.project.event.ProjectMemberAdded
import dev.knarusawa.dddDemo.testFactory.TestProjectFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull

@DisplayName("集約_プロジェクト")
class ProjectTest {
  @Nested
  @DisplayName("プロジェクトの作成")
  inner class CreateProject {
    @Test
    @DisplayName("コマンドから作成ができること")
    fun can_create_project() {
      val creator = MemberId.new()
      val cmd =
        CreateProjectCommand(
          projectName = ProjectName.of("テストプロジェクト"),
          created = creator,
        )

      val project = Project.create(cmd)

      assertEquals(project.projectName, ProjectName.of("テストプロジェクト"))
      assertEquals(project.getMembers().firstOrNull()?.memberId, creator)
      assertEquals(project.getMembers().firstOrNull()?.role, MemberRole.ADMIN)
      assertTrue(project.getEvents().any { it is ProjectCreated })
    }
  }

  @Nested
  @DisplayName("プロジェクトメンバーの追加")
  inner class AddProjectMember {
    @Test
    @DisplayName("メンバー追加コマンドで正しいイベントが返却されること")
    fun can_return_event() {
      val sut = TestProjectFactory.createProject()
      val added = ProjectMember.writeMember(MemberId.new())
      val cmd =
        AddProjectMemberCommand(
          projectId = sut.projectId,
          addedMembers = setOf(added),
        )

      val events = sut.handle(cmd)

      assertEquals(sut.getMembers().size, 1)
      assertTrue(events.any { it is ProjectMemberAdded })
      val event = events.firstOrNull() as ProjectMemberAdded
      assertEquals(1, event.addedMembers.size)
      assertEquals(added.memberId, event.addedMembers.firstOrNull()?.memberId)
      assertEquals(added.role, event.addedMembers.firstOrNull()?.role)
    }

    @Test
    @DisplayName("イベントの適用でメンバーが追加できること")
    fun can_add_member_from_event() {
      val sut = TestProjectFactory.createProject()
      val added = ProjectMember.writeMember(MemberId.new())
      val cmd =
        AddProjectMemberCommand(
          projectId = sut.projectId,
          addedMembers = setOf(added),
        )
      val events = sut.handle(cmd)
      val event = events.firstOrNull() as ProjectMemberAdded

      sut.apply(event)

      val actualAddedMember = sut.getMembers().find { it.memberId == added.memberId }
      assertNotNull(actualAddedMember)
      assertEquals(2, sut.getMembers().size)
      assertEquals(added.memberId, actualAddedMember.memberId)
      assertEquals(added.role, actualAddedMember.role)
    }

    @Test
    @DisplayName("既存のメンバーの追加でエラーにならないこと")
    fun can_add_already_exists_member_from_event() {
      val existsMember =
        ProjectMember.adminMember(
          memberId = MemberId.new(),
        )
      val sut = TestProjectFactory.createProject(members = mutableSetOf(existsMember))
      val added = ProjectMember.writeMember(existsMember.memberId)
      val cmd =
        AddProjectMemberCommand(
          projectId = sut.projectId,
          addedMembers = setOf(added),
        )
      val events = sut.handle(cmd)
      val event = events.firstOrNull() as ProjectMemberAdded

      sut.apply(event)

      val actualAddedMember = sut.getMembers().find { it.memberId == added.memberId }
      assertNotNull(actualAddedMember)
      assertEquals(1, sut.getMembers().size)
      assertEquals(added.memberId, actualAddedMember.memberId)
      assertEquals(added.role, actualAddedMember.role)
    }
  }
}
