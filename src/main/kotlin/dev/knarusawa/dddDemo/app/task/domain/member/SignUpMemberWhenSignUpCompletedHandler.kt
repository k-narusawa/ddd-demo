package dev.knarusawa.dddDemo.app.task.domain.member

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.event.UserSignupCompletedEvent
import dev.knarusawa.dddDemo.app.task.domain.actor.PersonalName
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class SignUpMemberWhenSignUpCompletedHandler(
  private val memberRepository: MemberRepository,
) {
  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  fun handle(event: UserSignupCompletedEvent) {
    val memberId = MemberId.from(value = event.user.userId.get())
    val foundMember = memberRepository.findById(memberId)
    if (foundMember.isEmpty) {
      val member =
        Member.signup(
          memberId = memberId,
          personalName = PersonalName.of(value = event.personalName),
        )
      memberRepository.save(member)
    }
  }
}
