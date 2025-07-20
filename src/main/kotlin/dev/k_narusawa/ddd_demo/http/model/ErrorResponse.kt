package dev.k_narusawa.ddd_demo.http.model

data class ErrorResponse(
  val title: String,
  val detail: String,
  val code: String? = null,
)