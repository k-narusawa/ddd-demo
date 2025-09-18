package dev.knarusawa.dddDemo.app.identityAccess.domain.user

import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.PLAccountStatus

enum class AccountStatus {
  NORMAL,
  ACCOUNT_LOCK,
  DEACTIVATED,
  ;

  companion object {
    fun of(value: String): AccountStatus =
      when (value) {
        "NORMAL" -> NORMAL
        "ACCOUNT_LOCK" -> ACCOUNT_LOCK
        "DEACTIVATED" -> DEACTIVATED
        else -> throw IllegalArgumentException("Invalid AccountStatus value: $value")
      }
  }

  fun toPublished() =
    when (this) {
      NORMAL -> PLAccountStatus.NORMAL
      ACCOUNT_LOCK -> PLAccountStatus.ACCOUNT_LOCK
      DEACTIVATED -> PLAccountStatus.DEACTIVATED
    }
}
