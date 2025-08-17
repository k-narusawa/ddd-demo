package dev.k_narusawa.ddd_demo.app.task.application.usecase.createTeam

import dev.k_narusawa.ddd_demo.app.task.application.port.CreateTeamInputBoundary
import dev.k_narusawa.ddd_demo.app.task.domain.team.Team
import dev.k_narusawa.ddd_demo.app.task.domain.team.TeamRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateTeamInteractor(
  private val teamRepository: TeamRepository,
  private val applicationEventPublisher: ApplicationEventPublisher,
) : CreateTeamInputBoundary {
  override suspend fun handle(input: CreateTeamInputData): CreateTeamOutputData {
    val team = Team.of(teamName = input.teamName)

    teamRepository.save(team = team)
    team.getEvents().forEach { event ->
      applicationEventPublisher.publishEvent(event)
    }

    return CreateTeamOutputData.of(team = team)
  }
}
