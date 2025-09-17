package dev.knarusawa.dddDemo.app.project.adapter.service

import dev.knarusawa.dddDemo.app.project.adapter.gateway.db.jpa.EventJpaRepository
import dev.knarusawa.dddDemo.util.logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(transactionManager = "projectTransactionManager")
class ProjectEventProcessor(
  private val eventJpaRepository: EventJpaRepository,
  private val eventHandler: EventPublishWhenEventListenedHandler,
) {
  companion object {
    private val log = logger()
  }

  fun handle() {
    val events = eventJpaRepository.findTop50ByPublishedAtIsNullOrderByOccurredAtAsc()
    events.forEach {
      try {
        eventHandler.handle(event = it)
      } catch (ex: Exception) {
        // NOTE: 本来トランザクションを開始したくないのでこうしてる
        // TransactionalアノテーションはDataSource切り替えのために必要
        log.error("Projectコンテキストのイベント発行に失敗", ex)
      }
    }
  }
}
