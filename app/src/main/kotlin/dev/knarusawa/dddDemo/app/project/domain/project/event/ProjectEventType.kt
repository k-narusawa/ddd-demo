package dev.knarusawa.dddDemo.app.project.domain.project.event

import dev.knarusawa.dddDemo.publishedLanguage.project.proto.PublishedLanguageProjectEventType

enum class ProjectEventType {
  PROJECT_CREATED,
  PROJECT_NAME_CHANGED,
  PROJECT_MEMBER_ADDED,
  ;

  fun toPublishedType(): PublishedLanguageProjectEventType =
    when (this) {
      PROJECT_CREATED -> PublishedLanguageProjectEventType.PROJECT_CREATED
      PROJECT_NAME_CHANGED -> PublishedLanguageProjectEventType.PROJECT_NAME_CHANGED
      PROJECT_MEMBER_ADDED -> PublishedLanguageProjectEventType.PROJECT_MEMBER_ADDED
    }
}
