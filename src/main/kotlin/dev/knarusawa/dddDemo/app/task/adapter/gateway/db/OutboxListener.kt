package dev.knarusawa.dddDemo.app.task.adapter.gateway.db

import dev.knarusawa.dddDemo.app.task.application.eventHandler.event.OutboxEvent
import dev.knarusawa.dddDemo.app.task.application.port.OutboxEventInputBoundary
import dev.knarusawa.dddDemo.util.logger
import jakarta.annotation.PostConstruct
import org.postgresql.PGConnection
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

@Component
class OutboxListener(
  private val dataSource: DataSource,
  private val outboxEventInputBoundary: OutboxEventInputBoundary,
) : ApplicationRunner {
  private lateinit var conn: Connection
  private lateinit var pgconn: PGConnection

  companion object {
    private val log = logger()
  }

  @PostConstruct
  fun init() {
    log.info("OutboxListenerを起動")
    this.conn = dataSource.connection
    this.pgconn = conn.unwrap(PGConnection::class.java)
    val stmt = conn.createStatement()
    stmt.execute("LISTEN outbox_channel;")
    stmt.close()
  }

  override fun run(args: ApplicationArguments) {
    try {
      while (true) {
        val notifications = pgconn.notifications

        if (notifications != null) {
          for (i in notifications.indices) {
            log.info(
              "PostgresSQLから通知受信 name: ${notifications[i]?.name}, payload: ${notifications[i]?.parameter}",
            )
            val event = OutboxEvent.of(payload = notifications[i]?.parameter)
            outboxEventInputBoundary.handle(event = event)
          }
        }
        Thread.sleep(500)
      }
    } catch (sqle: SQLException) {
      log.error("PostgresSQLからの通知処理に失敗", sqle)
    } catch (ie: InterruptedException) {
      log.error("PostgresSQLからの通知処理の中断", ie)
    }
  }
}
