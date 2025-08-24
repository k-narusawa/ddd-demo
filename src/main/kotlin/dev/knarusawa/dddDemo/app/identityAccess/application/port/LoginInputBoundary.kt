package dev.knarusawa.dddDemo.app.identityAccess.application.port

import dev.knarusawa.dddDemo.app.identityAccess.application.usecase.login.LoginInputData
import dev.knarusawa.dddDemo.app.identityAccess.application.usecase.login.LoginOutputData

interface LoginInputBoundary {
  suspend fun handle(input: LoginInputData): LoginOutputData
}
