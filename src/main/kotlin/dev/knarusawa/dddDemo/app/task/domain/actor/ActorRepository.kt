package dev.knarusawa.dddDemo.app.task.domain.actor

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActorRepository : JpaRepository<Actor, ActorId> {
  fun save(actor: Actor)

  fun findByActorId(actorId: ActorId): Actor?
}
