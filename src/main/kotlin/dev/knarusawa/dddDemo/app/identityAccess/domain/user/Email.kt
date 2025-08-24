package dev.knarusawa.dddDemo.app.identityAccess.domain.user

data class Email(
  val value: String,
) {
  init {
    require(value.isNotBlank()) { "Email cannot be blank" }
    require(value.matches(Regex("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+\""))) { "Invalid email format" }
  }
}
