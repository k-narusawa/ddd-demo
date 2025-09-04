package dev.knarusawa.dddDemo.app.task.adapter.controller

import dev.knarusawa.dddDemo.app.task.adapter.controller.model.CreateProjectRequest
import dev.knarusawa.dddDemo.app.task.adapter.controller.model.CreateProjectResponse
import dev.knarusawa.dddDemo.app.task.application.port.ProjectInputBoundary
import dev.knarusawa.dddDemo.app.task.application.service.IdentityAccessService
import dev.knarusawa.dddDemo.app.task.application.usecase.inputData.CreateProjectInputData
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/projects")
class ProjectController(
  private val identityAccessService: IdentityAccessService,
  private val projectInputBoundary: ProjectInputBoundary,
) {
  @PostMapping
  suspend fun post(
    @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false)
    authorization: String?,
    @RequestBody
    requestBody: CreateProjectRequest,
  ): ResponseEntity<CreateProjectResponse> {
    val token = authorization?.split(" ")[1]
    if (token == null) {
      return ResponseEntity.badRequest().build()
    }

    val introspect = identityAccessService.introspect(token = token)
    if (!introspect.active) {
      return ResponseEntity.badRequest().build()
    }

    val input =
      CreateProjectInputData.of(
        memberId = introspect.sub!!,
        projectName = requestBody.name,
      )

    val output = projectInputBoundary.handle(input = input)
    return ResponseEntity.ok(output.response)
  }
}
