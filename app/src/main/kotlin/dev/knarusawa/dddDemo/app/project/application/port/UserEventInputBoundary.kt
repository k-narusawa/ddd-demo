package dev.knarusawa.dddDemo.app.project.application.port

import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.UserEventMessage

interface UserEventInputBoundary {
  fun handle(event: UserEventMessage)
}
