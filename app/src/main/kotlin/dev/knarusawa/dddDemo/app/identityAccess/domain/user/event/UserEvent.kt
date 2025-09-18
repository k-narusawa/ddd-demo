package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.DomainEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.PLUserEvent
import dev.knarusawa.dddDemo.util.JsonUtil
import dev.knarusawa.dddDemo.util.ProtobufUtil
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
sealed class UserEvent : DomainEvent<PLUserEvent> {
  abstract override val eventId: UserEventId
  abstract val userId: UserId
  abstract val type: UserEventType
  abstract override val occurredAt: LocalDateTime

  companion object {
    fun deserialize(serialized: String) = JsonUtil.json.decodeFromString(serializer(), serialized)

    fun from(pl: PLUserEvent): UserEvent {
      val eventType = UserEventType.valueOf(pl.type.name)
      return when (eventType) {
        UserEventType.SIGNE_UP_COMPLETED -> SignupCompleted.from(pl)
        UserEventType.USERNAME_CHANGED -> UsernameChanged.from(pl)
        UserEventType.LOGIN_SUCCEEDED -> LoginSucceeded.from(pl)
        UserEventType.LOGIN_FAILED -> LoginFailed.from(pl)
      }
    }
  }

  override fun serialize() = JsonUtil.json.encodeToString(serializer(), this)

  override fun toPublishedLanguage(): PLUserEvent {
    val builder = PLUserEvent.newBuilder()
    builder.setEventId(eventId.get())
    builder.setUserId(userId.get())
    builder.setType(type.toPublishedType())
    builder.setOccurredAt(ProtobufUtil.toTimestamp(occurredAt))
    return builder.build()
  }
}
