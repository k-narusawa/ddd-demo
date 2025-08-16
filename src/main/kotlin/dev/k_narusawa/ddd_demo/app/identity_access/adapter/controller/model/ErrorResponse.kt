package dev.k_narusawa.ddd_demo.app.identity_access.adapter.controller.model

data class ErrorResponse(
  val title: String,
  val detail: String,
  val code: String? = null,
)
