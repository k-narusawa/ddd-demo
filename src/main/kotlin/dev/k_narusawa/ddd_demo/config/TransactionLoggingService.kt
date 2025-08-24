package dev.k_narusawa.ddd_demo.config

import dev.k_narusawa.ddd_demo.util.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class TransactionLoggingService {
  companion object {
    private val log = logger()
  }

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  fun beforeCommit(event: Any?) {
    log.info("Before transaction commit");
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  fun afterCommit(event: Any?) {
    log.info("After transaction commit");
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
  fun afterRollback(event: Any?) {
    log.info("After transaction rollback");
  }
}
