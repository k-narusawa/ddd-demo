package dev.k_narusawa.ddd_demo.middleware

import dev.k_narusawa.ddd_demo.util.logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Aspect
@Component
class DomainEventHandlerLoggingAspect {
  companion object {
    private val log = logger()
  }

  @Pointcut("@annotation(org.springframework.context.event.EventListener)")
  fun eventHandlerMethod() {
  }

  @Around("@annotation(org.springframework.context.event.EventListener)")
  fun measureExecutionTime(joinPoint: ProceedingJoinPoint): Any? {
    val className = joinPoint.signature.declaringTypeName.split(".").last()
    val executionTime = measureTimeMillis {
      joinPoint.proceed()
    }

    log.info(
      "イベントの処理時間: {} -> {}ms",
      className,
      executionTime
    )

    return joinPoint.proceed()
  }
}
