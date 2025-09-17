package dev.knarusawa.dddDemo.app.project.application.eventHandler

import dev.knarusawa.dddDemo.app.project.application.port.UserEventInputBoundary
import dev.knarusawa.dddDemo.app.project.domain.member.Member
import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.member.MemberRepository
import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.UserEventMessage
import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.UserEventType
import dev.knarusawa.dddDemo.util.logger
import org.springframework.stereotype.Component

@Component
class UserEventHandler(
  private val memberRepository: MemberRepository,
) : UserEventInputBoundary {
  companion object {
    private val log = logger()
  }

  override fun handle(event: UserEventMessage) {
    when (event.type) {
      UserEventType.SIGNE_UP_COMPLETED -> {
        val member = Member.signup(memberId = MemberId.from(value = event.userId))
        memberRepository.save(member = member)
      }

      else -> {
        log.info("ハンドリング対象外のイベント type: ${event.type}")
      }
    }
  }
}
