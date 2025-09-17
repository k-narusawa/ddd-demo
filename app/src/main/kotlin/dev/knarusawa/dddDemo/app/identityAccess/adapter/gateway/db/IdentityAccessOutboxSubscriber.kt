package dev.knarusawa.dddDemo.app.identityAccess.adapter.gateway.db

import dev.knarusawa.dddDemo.app.identityAccess.adapter.service.OutboxSubscribeEventHandler
import dev.knarusawa.dddDemo.infrastructure.RequestId
import dev.knarusawa.dddDemo.util.logger
import jakarta.annotation.PostConstruct
import org.postgresql.PGConnection
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

@Component
@Profile("!test") // FIXME: テスト実行時に動かすとテストできなくなったので一旦の暫定対応
class IdentityAccessOutboxSubscriber(
  @Qualifier("identityAccessDataSource") private val dataSource: DataSource,
  private val eventHandler: OutboxSubscribeEventHandler,
) : ApplicationRunner {
  private lateinit var conn: Connection
  private lateinit var pgconn: PGConnection

  companion object {
    private val log = logger()
  }

  @PostConstruct
  fun init() {
    log.info("IdentityAccessOutboxSubscriberを起動")
    this.conn = dataSource.connection
    this.pgconn = conn.unwrap(PGConnection::class.java)
    val stmt = conn.createStatement()
    stmt.execute("LISTEN outbox_channel;")
    stmt.close()
    log.info("IdentityAccessOutboxSubscriberの起動完了")
  }

  @Async // NOTE: ApplicationRunnerを実装したクラスを同時に起動しておくために必要
  override fun run(args: ApplicationArguments) {
    try {
      while (true) {
        val notifications = pgconn.notifications

        if (notifications != null) {
          for (i in notifications.indices) {
            try {
              RequestId.set()
              val eventId = notifications[i]?.parameter
              log.info(
                "PostgresSQLから通知受信 name: ${notifications[i]?.name}, eventId: $eventId",
              )
              eventHandler.handle()
            } catch (ex: Exception) {
              log.error("PostgresSQLからの通知処理に失敗", ex)
            } finally {
              RequestId.clear()
            }
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
