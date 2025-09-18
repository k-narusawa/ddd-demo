package dev.knarusawa.dddDemo.app.project.domain.exception

import dev.knarusawa.dddDemo.app.project.domain.project.ProjectId

class ProjectNotFound(
  override val message: String?,
  override val cause: Throwable? = null,
  val projectId: ProjectId? = null,
) : ProjectException(message, cause)
