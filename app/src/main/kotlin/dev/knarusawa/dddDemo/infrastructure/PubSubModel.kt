package dev.knarusawa.dddDemo.infrastructure

class PubSubModel private constructor(
  val topic: String,
  val subscriptions: List<String>,
) {
  companion object {
    const val TASK_EVENT_TOPIC = "task-event-topic"
    const val TASK_EVENT_SUBSCRIPTION = "$TASK_EVENT_TOPIC-task-subscription"

    fun of(): List<PubSubModel> =
      listOf(
        PubSubModel(
          topic = TASK_EVENT_TOPIC,
          subscriptions = listOf(TASK_EVENT_SUBSCRIPTION),
        ),
      )
  }
}
