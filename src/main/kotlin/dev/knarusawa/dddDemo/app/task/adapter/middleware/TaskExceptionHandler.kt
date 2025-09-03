package dev.knarusawa.dddDemo.app.task.adapter.middleware

import dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model.ErrorResponse
import dev.knarusawa.dddDemo.util.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["dev.knarusawa.dddDemo.app.task"])
class TaskExceptionHandler {
  companion object {
    private val log = logger()
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
  fun handleIllegalStateException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
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
