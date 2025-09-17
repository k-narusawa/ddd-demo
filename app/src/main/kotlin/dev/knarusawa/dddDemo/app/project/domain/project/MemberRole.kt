package dev.knarusawa.dddDemo.app.project.domain.project

import dev.knarusawa.dddDemo.publishedLanguage.project.proto.PublishedLanguageMemberRole

enum class MemberRole {
  ADMIN,
  WRITE,
  READ,
  ;

  fun toPublishedType(): PublishedLanguageMemberRole =
    when (this) {
      ADMIN -> PublishedLanguageMemberRole.ADMIN
      WRITE -> PublishedLanguageMemberRole.WRITE
      READ -> PublishedLanguageMemberRole.READ
    }
}
