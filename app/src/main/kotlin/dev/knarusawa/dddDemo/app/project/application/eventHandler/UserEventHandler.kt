package dev.knarusawa.dddDemo.app.project.application.eventHandler

import dev.knarusawa.dddDemo.app.project.application.port.UserEventInputBoundary
import dev.knarusawa.dddDemo.app.project.domain.member.Member
import dev.knarusawa.dddDemo.app.project.domain.member.MemberId
import dev.knarusawa.dddDemo.app.project.domain.member.MemberRepository
import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.PLUserEvent
import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.PLUserEventType
import dev.knarusawa.dddDemo.util.logger
import org.springframework.stereotype.Component

@Component
class UserEventHandler(
  private val memberRepository: MemberRepository,
) : UserEventInputBoundary {
  companion object {
    private val log = logger()
  }

  override fun handle(pl: PLUserEvent) {
    when (pl.type) {
      PLUserEventType.SIGNE_UP_COMPLETED -> {
        val member = Member.signup(memberId = MemberId.from(value = pl.userId))
        memberRepository.save(member = member)
      }

      else -> {
        log.info("ハンドリング対象外のイベント type: ${pl.type}")
      }
    }
  }
}
