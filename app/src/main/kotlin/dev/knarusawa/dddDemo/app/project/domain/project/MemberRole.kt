package dev.knarusawa.dddDemo.app.project.domain.project

import dev.knarusawa.dddDemo.publishedLanguage.project.proto.PLProjectMemberRole

enum class MemberRole {
  ADMIN,
  WRITE,
  READ,
  ;

  fun toPublishedType(): PLProjectMemberRole =
    when (this) {
      ADMIN -> PLProjectMemberRole.ADMIN
      WRITE -> PLProjectMemberRole.WRITE
      READ -> PLProjectMemberRole.READ
    }
}
