package dev.knarusawa.dddDemo.app.task.application.usecase.createTeam

import dev.knarusawa.dddDemo.app.task.application.port.TeamInputBoundary
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
    val team = Team.of(teamName = input.teamName, actorId = input.actorId)

    teamRepository.save(team = team)
    team.getEvents().forEach { event ->
      applicationEventPublisher.publishEvent(event)
    }

    return CreateTeamOutputData.of(team = team)
  }
}
