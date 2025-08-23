package dev.k_narusawa.ddd_demo.app.middleware

import dev.k_narusawa.ddd_demo.util.logger
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class DomainEventHandlerLoggingAspect {
  companion object {
    private val log = logger()
  }

  @Pointcut("@annotation(org.springframework.context.event.EventListener)")
  fun eventHandlerMethod() {
  }

  @Before("eventHandlerMethod() && args(event)")
  fun beforeHandleEvent(joinPoint: JoinPoint, event: Any) {
    val className = joinPoint.signature.declaringTypeName
    val methodName = joinPoint.signature.name
    log.info(
      "ドメインイベントの処理を開始: {}#{} with event {}",
      className,
      methodName,
      event.javaClass.simpleName
    )
  }

  @AfterReturning("eventHandlerMethod() && args(event)")
  fun afterHandleEvent(joinPoint: JoinPoint, event: Any) {
    val className = joinPoint.signature.declaringTypeName
    val methodName = joinPoint.signature.name
    log.info(
      "ドメインイベントの処理が完了: {}#{} with event {}",
      className,
      methodName,
      event.javaClass.simpleName
    )
  }
}
