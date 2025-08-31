package dev.knarusawa.dddDemo.infrastructure

class PubSubModel private constructor(
  val topic: String,
  val subscriptions: List<String>,
) {
  companion object {
    fun of(): List<PubSubModel> =
      listOf(
        PubSubModel(
          topic = "task-created-topic",
          subscriptions = listOf("task-sub"),
        ),
        PubSubModel(
          topic = "task-changed-topic",
          subscriptions = listOf("task-sub"),
        ),
        PubSubModel(
          topic = "task-completed-topic",
          subscriptions = listOf("task-sub"),
        ),
      )
  }
}
