package dev.knarusawa.dddDemo.util

import com.google.protobuf.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

object ProtobufUtil {
  private val OFFSET = ZoneOffset.UTC

  fun toTimestamp(localDateTime: LocalDateTime): Timestamp {
    val instant = localDateTime.atZone(OFFSET).toInstant()

    return Timestamp
      .newBuilder()
      .setSeconds(instant.epochSecond)
      .setNanos(instant.nano)
      .build()
  }

  fun toLocalDateTime(timestamp: Timestamp): LocalDateTime? {
    if (timestamp.seconds == 0L && timestamp.nanos == 0) { // NOTE: TimeStampはそのままだとnullにならないのでこうしておく
      return null
    }
    return Instant
      .ofEpochSecond(timestamp.seconds, timestamp.nanos.toLong())
      .atZone(OFFSET)
      .toLocalDateTime()
  }
}
