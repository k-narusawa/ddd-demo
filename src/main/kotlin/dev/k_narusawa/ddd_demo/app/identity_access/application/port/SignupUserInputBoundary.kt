package dev.k_narusawa.ddd_demo.app.identity_access.application.port

import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.signup.SignupUserInputData
import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.signup.SignupUserOutputData

interface SignupUserInputBoundary {
    fun handle(input: SignupUserInputData): SignupUserOutputData
}
