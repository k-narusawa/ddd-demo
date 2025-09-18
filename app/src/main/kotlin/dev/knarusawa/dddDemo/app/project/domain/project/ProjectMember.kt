package dev.knarusawa.dddDemo.app.project.domain.project

import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.publishedLanguage.project.proto.PLProjectMember

class ProjectMember private constructor(
  val memberId: MemberId,
  val role: MemberRole,
) {
  companion object {
    fun adminMember(memberId: MemberId) =
      ProjectMember(
        memberId = memberId,
        role = MemberRole.ADMIN,
      )

    fun writeMember(memberId: MemberId) =
      ProjectMember(
        memberId = memberId,
        role = MemberRole.WRITE,
      )

    fun readMember(memberId: MemberId) =
      ProjectMember(
        memberId = memberId,
        role = MemberRole.READ,
      )

    fun from(published: PLProjectMember) =
      ProjectMember(
        memberId = MemberId.from(published.memberId),
        role = MemberRole.valueOf(published.role.name),
      )
  }

  fun toPublishedLanguage(): PLProjectMember {
    val builder = PLProjectMember.newBuilder()
    builder.setMemberId(memberId.get())
    builder.setRole(role.toPublishedType())
    return builder.build()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is ProjectMember) return false
    if (memberId != other.memberId) return false
    return true
  }

  override fun hashCode() = memberId.hashCode()
}
