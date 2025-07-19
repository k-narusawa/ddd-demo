package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.registerUser

data class RegisterUserOutputData private constructor(
  val userId: String,
  val username: String
){
  companion object {
    fun of(userId: String, username: String): RegisterUserOutputData {
      return RegisterUserOutputData(
        userId = userId,
        username = username
      )
    }
  }
}