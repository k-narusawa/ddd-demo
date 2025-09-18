package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.user.User
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.Username
import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.PLUserEvent
import dev.knarusawa.dddDemo.util.ProtobufUtil
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UsernameChanged(
  override val eventId: UserEventId,
  override val userId: UserId,
  override val type: UserEventType,
  val username: Username,
  @Contextual
  override val occurredAt: LocalDateTime,
) : UserEvent() {
  companion object {
    fun of(user: User) =
      UsernameChanged(
        eventId = UserEventId.new(),
        userId = user.userId,
        type = UserEventType.USERNAME_CHANGED,
        username = user.getUsername(),
        occurredAt = LocalDateTime.now(),
      )

    fun from(pl: PLUserEvent) =
      UsernameChanged(
        eventId = UserEventId.from(value = pl.eventId),
        userId = UserId.from(value = pl.userId),
        type = UserEventType.valueOf(pl.type.name),
        username = Username.of(value = pl.username),
        occurredAt = ProtobufUtil.toLocalDateTime(pl.occurredAt)!!,
      )
  }

  override fun toPublishedLanguage(): PLUserEvent {
    val pl = super.toPublishedLanguage()
    val builder = pl.toBuilder()
    builder.setUsername(username.get())
    return builder.build()
  }
}
