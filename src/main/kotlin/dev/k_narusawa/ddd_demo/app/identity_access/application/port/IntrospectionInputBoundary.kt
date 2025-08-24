package dev.k_narusawa.ddd_demo.app.identity_access.application.port

import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.introspection.IntrospectionInputData
import dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.introspection.IntrospectionOutputData

interface IntrospectionInputBoundary {
    suspend fun handle(input: IntrospectionInputData): IntrospectionOutputData
}
