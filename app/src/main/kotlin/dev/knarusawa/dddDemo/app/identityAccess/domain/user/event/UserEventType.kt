package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.UserEventType as ProtoUserEventType

enum class UserEventType {
  SIGNE_UP_COMPLETED,
  USERNAME_CHANGED,
  LOGIN_SUCCEEDED,
  LOGIN_FAILED,
  ;

  fun toPublishedType() =
    when (this) {
      SIGNE_UP_COMPLETED -> ProtoUserEventType.SIGNE_UP_COMPLETED
      USERNAME_CHANGED -> ProtoUserEventType.USERNAME_CHANGED
      LOGIN_SUCCEEDED -> ProtoUserEventType.LOGIN_SUCCEEDED
      LOGIN_FAILED -> ProtoUserEventType.LOGIN_FAILED
    }

  companion object {
    fun isUserEventType(type: String): Boolean = entries.any { it.name == type }
  }
}
