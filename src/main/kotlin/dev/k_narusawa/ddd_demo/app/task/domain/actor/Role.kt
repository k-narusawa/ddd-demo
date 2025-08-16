package dev.k_narusawa.ddd_demo.app.task.domain.actor

sealed class Role {
  object Admin : Role()
  object Member : Role()
  object RoMember : Role()
  object Guest : Role()
}
