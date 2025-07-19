package dev.k_narusawa.ddd_demo.app.identity_access.application.port

import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.registerUser.RegisterUserInputData
import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.registerUser.RegisterUserOutputData

interface RegisterUserInputBoundary {
  suspend fun handle(input: RegisterUserInputData): RegisterUserOutputData
}