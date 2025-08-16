package dev.k_narusawa.ddd_demo.app.identity_access.application.usecase.introspection

import dev.k_narusawa.ddd_demo.app.identity_access.domain.user.UserId
import dev.k_narusawa.ddd_demo.http.model.IntrospectionResponse

data class IntrospectionOutputData private constructor(
  val response: IntrospectionResponse
) {
  companion object {
    fun of(
      sub: UserId? = null,
      active: Boolean,
    ) = IntrospectionOutputData(
      response = IntrospectionResponse(
        sub = sub?.get(),
        active = active
      )
    )
  }
}
