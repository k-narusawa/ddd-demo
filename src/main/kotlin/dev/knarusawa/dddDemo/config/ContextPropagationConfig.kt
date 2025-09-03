package dev.knarusawa.dddDemo.config

import dev.knarusawa.dddDemo.infrastructure.RequestId
import io.micrometer.context.ContextRegistry
import io.micrometer.context.ContextSnapshotFactory
import jakarta.annotation.PostConstruct
import org.slf4j.MDC
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Hooks

@Configuration
@ConditionalOnClass(value = [ContextRegistry::class, ContextSnapshotFactory::class])
class ContextPropagationConfig {
  @PostConstruct
  fun initialize() {
    ContextRegistry
      .getInstance()
      .registerThreadLocalAccessor(
        RequestId.HEADER_KEY,
        { MDC.get(RequestId.HEADER_KEY) },
        { value -> MDC.put(RequestId.HEADER_KEY, value) },
        { MDC.remove(RequestId.HEADER_KEY) },
      )

    Hooks.enableAutomaticContextPropagation()
  }
}
