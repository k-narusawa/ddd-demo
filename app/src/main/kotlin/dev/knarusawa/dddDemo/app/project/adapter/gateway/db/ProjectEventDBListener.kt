package dev.knarusawa.dddDemo.app.project.adapter.gateway.db

import dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa.AggregateType
import dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa.EventJpaRepository
import dev.knarusawa.dddDemo.app.project.adapter.gateway.message.TaskEventPublisher
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
import org.springframework.transaction.annotation.Transactional
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

@Component
@Profile("!test") // FIXME: テスト実行時に動かすとテストできなくなったので一旦の暫定対応
class ProjectEventDBListener(
  @Qualifier("projectDataSource") private val dataSource: DataSource,
  private val eventJpaRepository: EventJpaRepository,
  private val taskEventPublisher: TaskEventPublisher,
) : ApplicationRunner {
  private lateinit var conn: Connection
  private lateinit var pgconn: PGConnection

  companion object {
    private val log = logger()
  }

  @PostConstruct
  fun init() {
    log.info("ProjectEventDBListenerを起動")
    this.conn = dataSource.connection
    this.pgconn = conn.unwrap(PGConnection::class.java)
    val stmt = conn.createStatement()
    stmt.execute("LISTEN event_channel;")
    stmt.close()
    log.info("ProjectEventDBListenerの起動完了")
  }

  @Async // NOTE: ApplicationRunnerを実装したクラスを同時に起動しておくために必要
  @Transactional(transactionManager = "projectTransactionManager")
  override fun run(args: ApplicationArguments) {
    try {
      while (true) {
        val notifications = pgconn.notifications

        if (notifications != null) {
          for (i in notifications.indices) {
            try {
              RequestId.set()
              val eventId = notifications[i]?.parameter
              log.info("PostgresSQLから通知受信 eventId: $eventId")

              val events = eventJpaRepository.findTop50ByPublishedAtIsNullOrderByOccurredAtAsc()
              events.forEach {
                when (it.aggregateType) {
                  AggregateType.MEMBER -> {
                    TODO()
                  }

                  AggregateType.PROJECT -> {
                    TODO()
                  }

                  AggregateType.TASK -> {
                    taskEventPublisher.send(message = it.eventData)
                  }
                }
                it.published()
                eventJpaRepository.save(it)
              }
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
