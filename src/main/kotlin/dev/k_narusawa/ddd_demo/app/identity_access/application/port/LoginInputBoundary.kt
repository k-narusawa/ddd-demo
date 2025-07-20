package dev.k_narusawa.ddd_demo.app.identity_access.application.port

import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.login.LoginInputData

interface LoginInputBoundary {
  suspend fun handle(input: LoginInputData)
}