package dev.k_narusawa.ddd_demo.http.middleware

import dev.k_narusawa.ddd_demo.app.identity_access.exception.AuthenticationException
import dev.k_narusawa.ddd_demo.app.identity_access.exception.SignupException
import dev.k_narusawa.ddd_demo.http.model.ErrorResponse
import dev.k_narusawa.ddd_demo.util.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
  companion object {
    private val log = logger()
  }

  @ExceptionHandler(SignupException::class)
  fun handleSignupException(ex: SignupException): ResponseEntity<ErrorResponse> {
    log.warn("ユーザのサインアップに失敗しました", ex)
    val response = ErrorResponse(
      title = "ユーザのサインアップに失敗",
      detail = ex.message ?: "An error occurred due to invalid input",
    )

    return ResponseEntity
      .status(400)
      .header("Content-Type", "application/problem+json")
      .body(response)
  }

  @ExceptionHandler(AuthenticationException::class)
  fun handleAuthenticationException(ex: AuthenticationException): ResponseEntity<ErrorResponse> {
    log.warn("認証に失敗しました", ex)
    val response = ErrorResponse(
      title = "Authentication Failed",
      detail = ex.message ?: "An error occurred due to invalid input",
      code = when (ex.isLock) {
        true -> ""
        false -> ""
        null -> ""
      }
    )

    return ResponseEntity
      .status(401)
      .header("Content-Type", "application/problem+json")
      .body(response)
  }

  @ExceptionHandler(IllegalArgumentException::class)
  fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
    log.warn("IllegalArgumentException", ex)
    val response = ErrorResponse(
      title = "Invalid Request",
      detail = ex.message ?: "An error occurred due to invalid input",
      code = "INVALID_REQUEST"
    )

    return ResponseEntity
      .status(400)
      .header("Content-Type", "application/problem+json")
      .body(response)
  }

  @ExceptionHandler(IllegalStateException::class)
  fun handleIllegalStateException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
    log.warn("IllegalStateException", ex)
    val response = ErrorResponse(
      title = "Invalid State",
      detail = ex.message ?: "An error occurred due to an invalid state",
      code = "INVALID_STATE"
    )

    return ResponseEntity
      .status(400)
      .header("Content-Type", "application/problem+json")
      .body(response)
  }


  @ExceptionHandler(Exception::class)
  fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
    log.error("Internal Server Error", ex)
    val response = ErrorResponse(
      title = "Internal Server Error",
      detail = ex.message ?: "An unexpected error occurred",
      code = "INTERNAL_SERVER_ERROR"
    )

    return ResponseEntity
      .status(500)
      .header("Content-Type", "application/problem+json")
      .body(response)
  }
}
