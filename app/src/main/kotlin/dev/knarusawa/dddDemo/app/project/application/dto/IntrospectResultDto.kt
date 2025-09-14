package dev.knarusawa.dddDemo.app.project.application.dto

import dev.knarusawa.dddDemo.app.project.adapter.gateway.http.model.IntrospectionResponse

data class IntrospectResultDto(
  val sub: String?,
  val active: Boolean,
) {
  companion object {
    fun of(resp: IntrospectionResponse) =
      IntrospectResultDto(
        sub = resp.sub,
        active = resp.active,
      )
  }
}
