package dev.knarusawa.dddDemo.infrastructure

class PubSubModel private constructor(
  val topic: String,
  val subscriptions: List<String>,
) {
  companion object {
    const val TASK_CREATED_TOPIC = "task-created-topic"
    const val TASK_CHANGED_TOPIC = "task-changed-topic"
    const val TASK_COMPLETED_TOPIC = "task-completed-topic"

    const val TASK_SUBSCRIPTION = "task-sub"

    fun of(): List<PubSubModel> =
      listOf(
        PubSubModel(
          topic = TASK_CREATED_TOPIC,
          subscriptions = listOf(TASK_SUBSCRIPTION),
        ),
        PubSubModel(
          topic = TASK_CHANGED_TOPIC,
          subscriptions = listOf(TASK_SUBSCRIPTION),
        ),
        PubSubModel(
          topic = TASK_COMPLETED_TOPIC,
          subscriptions = listOf(TASK_SUBSCRIPTION),
        ),
      )
  }
}
