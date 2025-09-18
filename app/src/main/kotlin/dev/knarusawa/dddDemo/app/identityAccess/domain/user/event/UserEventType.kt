package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.PLUserEventType

enum class UserEventType {
  SIGNE_UP_COMPLETED,
  USERNAME_CHANGED,
  LOGIN_SUCCEEDED,
  LOGIN_FAILED,
  ;

  fun toPublishedType() =
    when (this) {
      SIGNE_UP_COMPLETED -> PLUserEventType.SIGNE_UP_COMPLETED
      USERNAME_CHANGED -> PLUserEventType.USERNAME_CHANGED
      LOGIN_SUCCEEDED -> PLUserEventType.LOGIN_SUCCEEDED
      LOGIN_FAILED -> PLUserEventType.LOGIN_FAILED
    }

  companion object {
    fun isUserEventType(type: String): Boolean = entries.any { it.name == type }
  }
}
