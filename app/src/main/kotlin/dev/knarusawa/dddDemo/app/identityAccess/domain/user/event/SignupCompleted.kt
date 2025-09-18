package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.AccountStatus
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.FamilyName
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.GivenName
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.LastLoginFailedAt
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.LoginFailureCount
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Username
import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.PLUserEvent
import dev.knarusawa.dddDemo.util.ProtobufUtil
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class SignupCompleted(
  override val eventId: UserEventId,
  override val userId: UserId,
  override val type: UserEventType,
  val username: Username,
  val emailVerified: Boolean,
  val givenName: GivenName,
  val familyName: FamilyName,
  val loginFailureCount: LoginFailureCount,
  val lastLoginFailedAt: LastLoginFailedAt,
  val accountStatus: AccountStatus,
  @Contextual
  override val occurredAt: LocalDateTime,
) : UserEvent() {
  companion object {
    fun of(user: User) =
      SignupCompleted(
        eventId = UserEventId.new(),
        userId = user.userId,
        type = UserEventType.SIGNE_UP_COMPLETED,
        username = user.getUsername(),
        emailVerified = user.getEmailVerified(),
        givenName = user.getGivenName(),
        familyName = user.getFamilyName(),
        loginFailureCount = user.getLoginFailureCount(),
        lastLoginFailedAt = user.getLastLoginFailedAt(),
        accountStatus = user.getAccountStatus(),
        occurredAt = LocalDateTime.now(),
      )

    fun from(pl: PLUserEvent) =
      SignupCompleted(
        eventId = UserEventId.from(value = pl.eventId),
        userId = UserId.from(value = pl.userId),
        type = UserEventType.valueOf(pl.type.name),
        username = Username.of(value = pl.username),
        emailVerified = pl.emailVerified,
        givenName = GivenName.of(value = pl.givenName!!),
        familyName = FamilyName.of(value = pl.familyName!!),
        loginFailureCount = LoginFailureCount.of(value = pl.loginFailureCount),
        lastLoginFailedAt =
          LastLoginFailedAt(ProtobufUtil.toLocalDateTime(pl.lastLoginFailedAt)),
        accountStatus = AccountStatus.valueOf(pl.accountStatus.name),
        occurredAt = ProtobufUtil.toLocalDateTime(pl.occurredAt)!!,
      )
  }

  override fun toPublishedLanguage(): PLUserEvent {
    val pl = super.toPublishedLanguage()
    val builder = pl.toBuilder()
    builder.setUsername(username.get())
    builder.setEmailVerified(emailVerified)
    builder.setGivenName(givenName.get())
    builder.setFamilyName(familyName.get())
    builder.setLoginFailureCount(loginFailureCount.get())
    lastLoginFailedAt.get()?.let {
      builder.setLastLoginFailedAt(ProtobufUtil.toTimestamp(it))
    }
    builder.setAccountStatus(accountStatus.toPublished())
    return builder.build()
  }
}
