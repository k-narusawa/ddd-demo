package dev.knarusawa.dddDemo.app.identityAccess.application.usecase.introspection

import dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model.IntrospectionResponse
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId

data class IntrospectionOutputData private constructor(
  val response: IntrospectionResponse,
) {
  companion object {
    fun of(
      sub: UserId? = null,
      active: Boolean,
    ) = IntrospectionOutputData(
      response =
        IntrospectionResponse(
          sub = sub?.get(),
          active = active,
        ),
    )
  }
}
