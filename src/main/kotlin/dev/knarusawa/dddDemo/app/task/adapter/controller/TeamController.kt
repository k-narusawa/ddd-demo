package dev.knarusawa.dddDemo.app.task.adapter.controller

import dev.knarusawa.dddDemo.app.task.adapter.controller.model.CreateTeamRequest
import dev.knarusawa.dddDemo.app.task.adapter.controller.model.CreateTeamResponse
import dev.knarusawa.dddDemo.app.task.application.port.TeamInputBoundary
import dev.knarusawa.dddDemo.app.task.application.service.IdentityAccessService
import dev.knarusawa.dddDemo.app.task.application.usecase.createTeam.CreateTeamInputData
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/task/teams")
class TeamController(
  private val identityAccessService: IdentityAccessService,
  private val teamInputBoundary: TeamInputBoundary,
) {
  @PostMapping
  suspend fun post(
    @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false)
    authorization: String?,
    @RequestBody
    requestBody: CreateTeamRequest,
  ): ResponseEntity<CreateTeamResponse> {
    val token = authorization?.split(" ")[1]
    if (token == null) {
      return ResponseEntity.badRequest().build()
    }

    val introspect = identityAccessService.introspect(token = token)
    if (!introspect.active) {
      return ResponseEntity.badRequest().build()
    }

    val input =
      CreateTeamInputData.of(
        memberId = introspect.sub!!,
        teamName = requestBody.name,
      )

    val output = teamInputBoundary.handle(input = input)
    return ResponseEntity.ok(output.response)
  }
}
