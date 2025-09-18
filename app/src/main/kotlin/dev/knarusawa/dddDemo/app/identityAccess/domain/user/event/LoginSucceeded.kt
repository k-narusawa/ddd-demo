package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.AccountStatus
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.LastLoginFailedAt
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.LoginFailureCount
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.PLUserEvent
import dev.knarusawa.dddDemo.util.ProtobufUtil
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class LoginSucceeded(
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
    ) = LoginSucceeded(
      eventId = UserEventId.new(),
      userId = user.userId,
      type = UserEventType.LOGIN_SUCCEEDED,
      loginFailureCount = user.getLoginFailureCount(),
      lastLoginFailedAt = user.getLastLoginFailedAt(),
      accountStatus = user.getAccountStatus(),
      userAgent = userAgent,
      ipAddress = ipAddress,
      occurredAt = LocalDateTime.now(),
    )

    fun from(pl: PLUserEvent) =
      LoginSucceeded(
        eventId = UserEventId.from(value = pl.eventId),
        userId = UserId.from(value = pl.userId),
        type = UserEventType.valueOf(pl.type.name),
        loginFailureCount = LoginFailureCount.of(value = pl.loginFailureCount),
        lastLoginFailedAt =
          LastLoginFailedAt(ProtobufUtil.toLocalDateTime(pl.lastLoginFailedAt)),
        accountStatus = AccountStatus.valueOf(pl.accountStatus.name),
        userAgent = pl.userAgent,
        ipAddress = pl.ipAddress,
        occurredAt = ProtobufUtil.toLocalDateTime(pl.occurredAt)!!,
      )
  }

  override fun toPublishedLanguage(): PLUserEvent {
    val pl = super.toPublishedLanguage()
    val builder = pl.toBuilder()
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
