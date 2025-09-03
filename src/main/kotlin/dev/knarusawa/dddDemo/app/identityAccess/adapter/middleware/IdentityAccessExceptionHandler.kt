package dev.knarusawa.dddDemo.app.identityAccess.adapter.middleware

import dev.knarusawa.dddDemo.app.identityAccess.adapter.controller.model.ErrorResponse
import dev.knarusawa.dddDemo.app.identityAccess.application.exception.IdentityAccessApplicationException
import dev.knarusawa.dddDemo.app.identityAccess.application.exception.UsernameAlreadyExists
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.AccountLock
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.IdentityAccessDomainException
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.LoginFailed
import dev.knarusawa.dddDemo.app.identityAccess.domain.exception.TokenUnauthorized
import dev.knarusawa.dddDemo.util.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["dev.knarusawa.dddDemo.app.identityAccess"])
class IdentityAccessExceptionHandler {
  companion object {
    private val log = logger()
  }

  @ExceptionHandler(IdentityAccessDomainException::class)
  fun handleDomainException(ex: IdentityAccessDomainException): ResponseEntity<ErrorResponse> {
    when (ex) {
      is LoginFailed -> {
        log.warn("ログインに失敗しました userId: ${ex.userId?.get()}")
        val response =
          ErrorResponse(
            title = "unauthorized",
            detail = "unauthorized",
          )

        return ResponseEntity
          .status(401)
          .header("Content-Type", "application/problem+json")
          .body(response)
      }

      is AccountLock -> {
        log.warn("アカウントがロックされています userId: ${ex.userId?.get()}")
        val response =
          ErrorResponse(
            title = "account is locked.",
            detail = "account id locked.",
          )

        return ResponseEntity
          .status(401)
          .header("Content-Type", "application/problem+json")
          .body(response)
      }

      is TokenUnauthorized -> {
        log.warn("認証に失敗しました", ex)
        val response =
          ErrorResponse(
            title = "token is invalid.",
            detail = "token is invalid.",
          )

        return ResponseEntity
          .status(401)
          .header("Content-Type", "application/problem+json")
          .body(response)
      }

      else -> {
        log.error("予期せぬエラーが発生しました", ex)
        val response =
          ErrorResponse(
            title = "予期せぬエラーが発生しました",
            detail = ex.message ?: "An error occurred due to invalid input",
          )

        return ResponseEntity
          .status(500)
          .header("Content-Type", "application/problem+json")
          .body(response)
      }
    }
  }

  @ExceptionHandler(IdentityAccessApplicationException::class)
  fun handleApplicationException(
    ex: IdentityAccessApplicationException,
  ): ResponseEntity<ErrorResponse> {
    when (ex) {
      is UsernameAlreadyExists -> {
        log.warn("すでに登録ずみのUsernameです", ex.message)
        val response =
          ErrorResponse(
            title = "signup failed.",
            detail = "signup failed.",
          )

        return ResponseEntity
          .status(400)
          .header("Content-Type", "application/problem+json")
          .body(response)
      }

      else -> {
        log.error("予期せぬエラーが発生しました", ex.message)
        val response =
          ErrorResponse(
            title = "予期せぬエラーが発生しました",
            detail = ex.message ?: "An error occurred due to invalid input",
          )

        return ResponseEntity
          .status(500)
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
