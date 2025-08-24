package dev.knarusawa.dddDemo.app.identityAccess.application.port

import dev.knarusawa.dddDemo.app.identityAccess.application.usecase.introspection.IntrospectionInputData
import dev.knarusawa.dddDemo.app.identityAccess.application.usecase.introspection.IntrospectionOutputData

interface IntrospectionInputBoundary {
  suspend fun handle(input: IntrospectionInputData): IntrospectionOutputData
}
