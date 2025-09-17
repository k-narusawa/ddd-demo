package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.AccountStatus
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.LastLoginFailedAt
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.LoginFailureCount
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.UserEventMessage
import dev.knarusawa.dddDemo.util.ProtobufUtil
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class LoginFailed(
  override val eventId: UserEventId,
  override val userId: UserId,
  override val type: UserEventType,
  val loginFailureCount: LoginFailureCount,
  val lastLoginFailedAt: LastLoginFailedAt,
  val accountStatus: AccountStatus,
  val userAgent: String,
  val ipAddress: String,
  @Contextual
  override val occurredAt: LocalDateTime,
) : UserEvent() {
  companion object {
    fun of(
      user: User,
      userAgent: String,
      ipAddress: String,
    ) = LoginFailed(
      eventId = UserEventId.new(),
      userId = user.userId,
      type = UserEventType.LOGIN_FAILED,
      loginFailureCount = user.getLoginFailureCount(),
      lastLoginFailedAt = user.getLastLoginFailedAt(),
      accountStatus = user.getAccountStatus(),
      userAgent = userAgent,
      ipAddress = ipAddress,
      occurredAt = LocalDateTime.now(),
    )

    fun from(eventMessage: UserEventMessage) =
      LoginFailed(
        eventId = UserEventId.from(value = eventMessage.eventId),
        userId = UserId.from(value = eventMessage.userId),
        type = UserEventType.valueOf(eventMessage.type.name),
        loginFailureCount = LoginFailureCount.of(value = eventMessage.loginFailureCount),
        lastLoginFailedAt =
          LastLoginFailedAt(ProtobufUtil.toLocalDateTime(eventMessage.lastLoginFailedAt)),
        accountStatus = AccountStatus.valueOf(eventMessage.accountStatus.name),
        userAgent = eventMessage.userAgent,
        ipAddress = eventMessage.ipAddress,
        occurredAt = ProtobufUtil.toLocalDateTime(eventMessage.occurredAt)!!,
      )
  }

  override fun toEventMessage(): UserEventMessage {
    val message = super.toEventMessage()
    val builder = message.toBuilder()
    builder.setLoginFailureCount(loginFailureCount.get())
    lastLoginFailedAt.get()?.let {
      builder.setLastLoginFailedAt(ProtobufUtil.toTimestamp(it))
    }
    builder.setAccountStatus(accountStatus.toPublished())
    builder.setUserAgent(userAgent)
    builder.setIpAddress(ipAddress)
    return builder.build()
  }
}
