package dev.knarusawa.dddDemo.app.project.domain.exception

sealed class ProjectException(
  override val message: String?,
  override val cause: Throwable?,
) : RuntimeException(message, cause)
