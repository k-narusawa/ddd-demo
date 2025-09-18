package dev.knarusawa.dddDemo.app.project.adapter.middleware

import dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model.ErrorResponse
import dev.knarusawa.dddDemo.app.project.domain.exception.ProjectException
import dev.knarusawa.dddDemo.app.project.domain.exception.ProjectNotFound
import dev.knarusawa.dddDemo.util.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["dev.knarusawa.dddDemo.app.project"])
class ProjectExceptionHandler {
  companion object {
    private val log = logger()
  }

  @ExceptionHandler(ProjectException::class)
  fun handleProjectException(ex: ProjectException): ResponseEntity<ErrorResponse> {
    log.warn("ProjectException", ex)

    when (ex) {
      is ProjectNotFound -> {
        val response =
          ErrorResponse(
            title = "ProjectNotFound",
            detail = "Project NotFound.",
            code = "PROJECT_NOT_FOUND",
          )

        return ResponseEntity
          .status(404)
          .header("Content-Type", "application/problem+json")
          .body(response)
      }
    }
  }

  @ExceptionHandler(IllegalArgumentException::class)
  fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
    log.warn("IllegalArgumentException", ex)
    val response =
      ErrorResponse(
        title = "Invalid Request",
        detail = ex.message ?: "An error occurred due to invalid input",
        code = "INVALID_REQUEST",
      )

    return ResponseEntity
      .status(400)
      .header("Content-Type", "application/problem+json")
      .body(response)
  }

  @ExceptionHandler(IllegalStateException::class)
  fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<ErrorResponse> {
    log.warn("IllegalStateException", ex)
    val response =
      ErrorResponse(
        title = "Invalid State",
        detail = ex.message ?: "An error occurred due to an invalid state",
        code = "INVALID_STATE",
      )

    return ResponseEntity
      .status(400)
      .header("Content-Type", "application/problem+json")
      .body(response)
  }

  @ExceptionHandler(Exception::class)
  fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
    log.error("Internal Server Error", ex)
    val response =
      ErrorResponse(
        title = "Internal Server Error",
        detail = ex.message ?: "An unexpected error occurred",
        code = "INTERNAL_SERVER_ERROR",
      )

    return ResponseEntity
      .status(500)
      .header("Content-Type", "application/problem+json")
      .body(response)
  }
}
