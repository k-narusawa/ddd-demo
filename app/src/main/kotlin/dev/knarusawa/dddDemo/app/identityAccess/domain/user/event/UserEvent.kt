package dev.knarusawa.dddDemo.app.identityAccess.domain.user.event

import dev.knarusawa.dddDemo.app.identityAccess.domain.DomainEvent
import dev.knarusawa.dddDemo.app.identityAccess.domain.user.UserId
import dev.knarusawa.dddDemo.publishedLanguage.identityAccess.proto.UserEventMessage
import dev.knarusawa.dddDemo.util.JsonUtil
import dev.knarusawa.dddDemo.util.ProtobufUtil
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
sealed class UserEvent : DomainEvent<UserEventMessage> {
  abstract override val eventId: UserEventId
  abstract val userId: UserId
  abstract val type: UserEventType
  abstract override val occurredAt: LocalDateTime

  companion object {
    fun deserialize(serialized: String) = JsonUtil.json.decodeFromString(serializer(), serialized)

    fun from(eventMessage: UserEventMessage): UserEvent {
      val eventType = UserEventType.valueOf(eventMessage.type.name)
      return when (eventType) {
        UserEventType.SIGNE_UP_COMPLETED -> SignupCompleted.from(eventMessage)
        UserEventType.USERNAME_CHANGED -> UsernameChanged.from(eventMessage)
        UserEventType.LOGIN_SUCCEEDED -> LoginSucceeded.from(eventMessage)
        UserEventType.LOGIN_FAILED -> LoginFailed.from(eventMessage)
      }
    }
  }

  override fun serialize() = JsonUtil.json.encodeToString(serializer(), this)

  override fun toEventMessage(): UserEventMessage {
    val builder = UserEventMessage.newBuilder()
    builder.setEventId(eventId.get())
    builder.setUserId(userId.get())
    builder.setType(type.toPublishedType())
    builder.setOccurredAt(ProtobufUtil.toTimestamp(occurredAt))
    return builder.build()
  }
}
