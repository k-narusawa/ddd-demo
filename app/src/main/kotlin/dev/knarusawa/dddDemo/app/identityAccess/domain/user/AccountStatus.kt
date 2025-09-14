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
}
