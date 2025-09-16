package dev.knarusawa.dddDemo.app.identityAccess.application.port

import dev.knarusawa.dddDemo.app.identityAccess.application.usecase.signup.SignupUserInputData
import dev.knarusawa.dddDemo.app.identityAccess.application.usecase.signup.SignupUserOutputData

interface SignupUserInputBoundary {
  fun handle(input: SignupUserInputData): SignupUserOutputData
}
