package dev.knarusawa.dddDemo.app.identityAccess.domain.user

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
      NORMAL ->
        dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.AccountStatus.NORMAL

      ACCOUNT_LOCK ->
        dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.AccountStatus.ACCOUNT_LOCK

      DEACTIVATED ->
        dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.AccountStatus.DEACTIVATED
    }
}
