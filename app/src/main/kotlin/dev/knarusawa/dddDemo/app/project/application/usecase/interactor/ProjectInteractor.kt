package dev.knarusawa.dddDemo.app.project.application.usecase.interactor

import dev.knarusawa.dddDemo.app.project.application.port.ProjectInputBoundary
import dev.knarusawa.dddDemo.app.project.application.usecase.inputData.CreateProjectInputData
import dev.knarusawa.dddDemo.app.project.application.usecase.outputData.CreateProjectOutputData
import dev.knarusawa.dddDemo.app.project.domain.project.Project
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProjectInteractor(
  private val projectRepository: ProjectRepository,
  private val applicationEventPublisher: ApplicationEventPublisher,
) : ProjectInputBoundary {
  override fun handle(input: CreateProjectInputData): CreateProjectOutputData {
    val project = Project.of(projectName = input.projectName, memberId = input.memberId)

    projectRepository.save(project = project)
    project.getEvents().forEach { event ->
      applicationEventPublisher.publishEvent(event)
    }

    return CreateProjectOutputData.of(project = project)
  }
}
