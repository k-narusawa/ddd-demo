package dev.knarusawa.dddDemo.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskChanged
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCompleted
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskCreated
import dev.knarusawa.dddDemo.app.task.domain.task.event.TaskEvent
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object JsonUtil {
  val objectMapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())

  fun jsonToMap(jsonString: String): Map<String, Any> = objectMapper.readValue(jsonString)

  internal object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor =
      PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(
      encoder: Encoder,
      value: LocalDateTime,
    ) {
      encoder.encodeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime =
      LocalDateTime.parse(decoder.decodeString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
  }

  private val serializersModule =
    SerializersModule {
      contextual(LocalDateTimeSerializer)
      polymorphic(TaskEvent::class) {
        subclass(TaskCreated::class)
        subclass(TaskChanged::class)
        subclass(TaskCompleted::class)
      }
    }

  val json =
    Json {
      ignoreUnknownKeys = true
      prettyPrint = true
      serializersModule = JsonUtil.serializersModule
      classDiscriminator = "@type"
    }
}
