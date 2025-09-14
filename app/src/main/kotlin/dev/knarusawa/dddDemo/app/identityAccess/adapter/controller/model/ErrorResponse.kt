package dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model

data class ErrorResponse(
  val title: String,
  val detail: String,
  val code: String? = null,
)
