package dev.knarusawa.dddDemo.app.project.domain.project.event

import dev.knarusawa.dddDemo.publishedLanguage.project.proto.PLProjectEventType

enum class ProjectEventType {
  PROJECT_CREATED,
  PROJECT_NAME_CHANGED,
  PROJECT_MEMBER_ADDED,
  ;

  fun toPublishedType(): PLProjectEventType =
    when (this) {
      PROJECT_CREATED -> PLProjectEventType.PROJECT_CREATED
      PROJECT_NAME_CHANGED -> PLProjectEventType.PROJECT_NAME_CHANGED
      PROJECT_MEMBER_ADDED -> PLProjectEventType.PROJECT_MEMBER_ADDED
    }
}
