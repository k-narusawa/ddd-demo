package dev.knarusawa.dddDemo.app.task.domain.actor

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UserSignupCompletedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class SignUpActorWhenSignUpCompletedHandler(
  private val actorRepository: ActorRepository,
) {
  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  fun handle(event: UserSignupCompletedEvent) {
    val actorId = ActorId.from(value = event.user.userId.get())
    val foundActor = actorRepository.findById(actorId)
    if (foundActor.isEmpty) {
      val actor =
        Actor.signup(
          actorId = actorId,
          personalName = PersonalName.of(value = event.personalName),
        )
      actorRepository.save(actor)
    }
  }
}
