package dev.knarusawa.dddDemo.app.project.application.port

import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.PLUserEvent

interface UserEventInputBoundary {
  fun handle(pl: PLUserEvent)
}
