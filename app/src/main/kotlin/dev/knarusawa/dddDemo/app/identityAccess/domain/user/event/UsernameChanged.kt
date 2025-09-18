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

    fun from(eventMessage: PLUserEvent) =
      UsernameChanged(
        eventId = UserEventId.from(value = eventMessage.eventId),
        userId = UserId.from(value = eventMessage.userId),
        type = UserEventType.valueOf(eventMessage.type.name),
        username = Username.of(value = eventMessage.username),
        occurredAt = ProtobufUtil.toLocalDateTime(eventMessage.occurredAt)!!,
      )
  }

  override fun toEventMessage(): PLUserEvent {
    val message = super.toEventMessage()
    val builder = message.toBuilder()
    builder.setUsername(username.get())
    return builder.build()
  }
}
