package dev.knarusawa.dddDemo.app.task.adapter.gateway.postgresql

import dev.knarusawa.dddDemo.util.logger
import jakarta.annotation.PostConstruct
import org.postgresql.PGConnection
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Component
class OutBoxListener(
  private val dataSource: DataSource,
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
    val stmt: Statement = conn.createStatement()
    stmt.execute("LISTEN outbox_channel;")
    stmt.close()
  }

  override fun run(args: ApplicationArguments) {
    try {
      while (true) {
        val notifications = pgconn.notifications

        if (notifications != null) {
          for (i in notifications.indices) {
            println(
              "Got notification: " + notifications[i]?.name,
            )
          }
        }

        Thread.sleep(500)
      }
    } catch (sqle: SQLException) {
      sqle.printStackTrace()
    } catch (ie: InterruptedException) {
      ie.printStackTrace()
    }
  }
}
