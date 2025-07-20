package dev.k_narusawa.ddd_demo.http.middleware

import dev.k_narusawa.ddd_demo.http.model.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
  @ExceptionHandler(IllegalArgumentException::class)
  fun handleIllegalArgumentException(ex: Exception): ResponseEntity<ErrorResponse> {
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
  fun handleIllegalStateException(ex: Exception): ResponseEntity<ErrorResponse> {
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