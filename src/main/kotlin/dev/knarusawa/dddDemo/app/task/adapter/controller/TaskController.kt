package dev.knarusawa.dddDemo.app.task.adapter.controller

import dev.knarusawa.dddDemo.app.task.adapter.controller.model.CreateTaskRequest
import dev.knarusawa.dddDemo.app.task.adapter.controller.model.TaskListResponse
import dev.knarusawa.dddDemo.app.task.adapter.controller.model.TaskResponse
import dev.knarusawa.dddDemo.app.task.application.port.TaskInputBoundary
import dev.knarusawa.dddDemo.app.task.application.readModel.TaskReadModelQueryService
import dev.knarusawa.dddDemo.app.task.application.service.IdentityAccessService
import dev.knarusawa.dddDemo.app.task.application.usecase.inputData.ChangeTaskInputData
import dev.knarusawa.dddDemo.app.task.application.usecase.inputData.CreateTaskInputData
import dev.knarusawa.dddDemo.app.task.domain.project.ProjectId
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
class TaskController(
  private val identityAccessService: IdentityAccessService,
  private val taskInputBoundary: TaskInputBoundary,
  private val taskReadModelQueryService: TaskReadModelQueryService,
) {
  @GetMapping
  suspend fun get(
    @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false)
    authorization: String?,
    @PathVariable(name = "projectId")
    projectId: String,
  ): ResponseEntity<TaskListResponse> {
    val token = authorization?.split(" ")[1]
    if (token == null) {
      return ResponseEntity.badRequest().build()
    }

    val introspect = identityAccessService.introspect(token = token)
    if (!introspect.active) {
      return ResponseEntity.badRequest().build()
    }

    val models =
      taskReadModelQueryService.findByProjectId(projectId = ProjectId.from(value = projectId))

    val response =
      models.map {
        TaskListResponse.TaskResponse(
          taskId = it.taskId.get(),
          projectId = it.projectId.get(),
          operator = it.operator.get(),
          title = it.title.get(),
          description = it.description?.get(),
          assigner = it.assigner?.get(),
          assignee = it.assignee?.get(),
          fromTime = it.fromTime?.get(),
          toTime = it.toTime?.get(),
        )
      }

    return ResponseEntity.status(200).body(TaskListResponse(items = response))
  }

  @PostMapping
  suspend fun post(
    @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false)
    authorization: String?,
    @PathVariable(name = "projectId")
    projectId: String,
    @RequestBody
    body: CreateTaskRequest,
  ): ResponseEntity<TaskResponse> {
    val token = authorization?.split(" ")[1]
    if (token == null) {
      return ResponseEntity.badRequest().build()
    }

    val introspect = identityAccessService.introspect(token = token)
    if (!introspect.active) {
      return ResponseEntity.badRequest().build()
    }

    val input =
      CreateTaskInputData.of(
        projectId = projectId,
        operator = introspect.sub!!,
        title = body.title,
        description = body.description,
        assigner = body.assigner,
        assignee = body.assignee,
        fromTime = body.fromTime,
        toTime = body.toTime,
      )
    val output = taskInputBoundary.handle(input = input)

    return ResponseEntity.status(201).body(output.response)
  }

  @PutMapping("/{taskId}")
  suspend fun put(
    @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false)
    authorization: String?,
    @PathVariable(name = "projectId")
    projectId: String,
    @PathVariable(name = "taskId")
    taskId: String,
    @RequestBody
    body: CreateTaskRequest,
  ): ResponseEntity<TaskResponse> {
    val token = authorization?.split(" ")[1]
    if (token == null) {
      return ResponseEntity.badRequest().build()
    }

    val introspect = identityAccessService.introspect(token = token)
    if (!introspect.active) {
      return ResponseEntity.badRequest().build()
    }

    val input =
      ChangeTaskInputData.of(
        taskId = taskId,
        projectId = projectId,
        operator = introspect.sub!!,
        title = body.title,
        description = body.description,
        assigner = body.assigner,
        assignee = body.assignee,
        fromTime = body.fromTime,
        toTime = body.toTime,
      )
    val output = taskInputBoundary.handle(input = input)

    return ResponseEntity.status(200).body(output.response)
  }
}
