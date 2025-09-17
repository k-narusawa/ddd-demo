package dev.knarusawa.dddDemo.infrastructure

class PubSubModel private constructor(
  val topic: String,
  val subscriptions: List<String>,
) {
  companion object {
    const val IDENTITY_ACCESS_CONTEXT_PREFIX = "identity_access"
    const val PROJECT_CONTEXT_PREFIX = "project"

    const val USER_EVENT_TOPIC = "user-event-topic"
    const val IDENTITY_ACCESS_USER_EVENT_SUBSCRIPTION =
      "$IDENTITY_ACCESS_CONTEXT_PREFIX-$USER_EVENT_TOPIC-task-subscription"
    const val PROJECT_USER_EVENT_SUBSCRIPTION =
      "$PROJECT_CONTEXT_PREFIX-$USER_EVENT_TOPIC-task-subscription"

    const val TASK_EVENT_TOPIC = "task-event-topic"
    const val PROJECT_TASK_EVENT_SUBSCRIPTION =
      "$PROJECT_CONTEXT_PREFIX-$TASK_EVENT_TOPIC-task-subscription"

    fun of(): List<PubSubModel> =
      listOf(
        PubSubModel(
          topic = USER_EVENT_TOPIC,
          subscriptions =
            listOf(
              IDENTITY_ACCESS_USER_EVENT_SUBSCRIPTION,
              PROJECT_USER_EVENT_SUBSCRIPTION,
            ),
        ),
        PubSubModel(
          topic = TASK_EVENT_TOPIC,
          subscriptions =
            listOf(
              PROJECT_TASK_EVENT_SUBSCRIPTION,
            ),
        ),
      )
  }
}
