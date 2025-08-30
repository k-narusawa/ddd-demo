package dev.knarusawa.dddDemo.app.task.application.usecase.interactor

import dev.knarusawa.dddDemo.app.task.application.port.TeamInputBoundary
import dev.knarusawa.dddDemo.app.task.application.usecase.inputData.CreateTeamInputData
import dev.knarusawa.dddDemo.app.task.application.usecase.outputData.CreateTeamOutputData
import dev.knarusawa.dddDemo.app.task.domain.team.Team
import dev.knarusawa.dddDemo.app.task.domain.team.TeamRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TeamInteractor(
  private val teamRepository: TeamRepository,
  private val applicationEventPublisher: ApplicationEventPublisher,
) : TeamInputBoundary {
  override fun handle(input: CreateTeamInputData): CreateTeamOutputData {
    val team = Team.Companion.of(teamName = input.teamName, memberId = input.memberId)

    teamRepository.save(team = team)
    team.getEvents().forEach { event ->
      applicationEventPublisher.publishEvent(event)
    }

    return CreateTeamOutputData.Companion.of(team = team)
  }
}
