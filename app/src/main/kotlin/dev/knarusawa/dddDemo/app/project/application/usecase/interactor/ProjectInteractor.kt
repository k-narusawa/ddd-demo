package dev.knarusawa.dddDemo.app.project.application.usecase.interactor

import dev.knarusawa.dddDemo.app.project.application.port.ProjectInputBoundary
import dev.knarusawa.dddDemo.app.project.application.usecase.inputData.CreateProjectInputData
import dev.knarusawa.dddDemo.app.project.application.usecase.outputData.CreateProjectOutputData
import dev.knarusawa.dddDemo.app.project.domain.project.Project
import dev.knarusawa.dddDemo.app.project.domain.project.ProjectRepository
import dev.knarusawa.dddDemo.app.project.domain.project.command.CreateProjectCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProjectInteractor(
  private val projectRepository: ProjectRepository,
) : ProjectInputBoundary {
  override fun handle(input: CreateProjectInputData): CreateProjectOutputData {
    val cmd =
      CreateProjectCommand(
        projectName = input.projectName,
        created = input.memberId,
      )

    val project = Project.create(cmd = cmd)
    project.getEvents().forEach {
      projectRepository.save(event = it)
    }

    return CreateProjectOutputData.of(project = project)
  }
}
