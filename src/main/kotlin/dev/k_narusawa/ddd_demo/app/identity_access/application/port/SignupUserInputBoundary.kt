package dev.k_narusawa.ddd_demo.app.identity_access.application.port

import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.registerUser.SignupUserInputData
import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.registerUser.SignupUserOutputData

interface SignupUserInputBoundary {
  suspend fun handle(input: SignupUserInputData): SignupUserOutputData
}
