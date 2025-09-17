package dev.knarusawa.dddDemo.app.identityAccess.adapter.service

import dev.knarusawa.dddDemo.app.identityAccess.domain.outbox.IdentityAccessOutboxRepository
import dev.knarusawa.dddDemo.util.logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(transactionManager = "identityAccessTransactionManager")
class OutboxProcessor(
  private val outboxRepository: IdentityAccessOutboxRepository,
  private val eventHandler: EventPublishWhenOutboxEventListenedHandler,
) {
  companion object {
    private val log = logger()
  }

  fun handle() {
    val outboxContents = outboxRepository.findTop50ByProcessedAtIsNullOrderByOccurredAtAsc()
    outboxContents.forEach {
      try {
        eventHandler.handle(outbox = it)
      } catch (ex: Exception) {
        // NOTE: 本来トランザクションを開始したくないのでこうしてる
        // TransactionalアノテーションはDataSource切り替えのために必要
        log.error("Outboxからのイベント発行に失敗", ex)
      }
    }
  }
}
