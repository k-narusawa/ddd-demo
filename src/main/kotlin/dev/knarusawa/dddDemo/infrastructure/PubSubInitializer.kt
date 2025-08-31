package dev.knarusawa.dddDemo.infrastructure

import com.google.api.gax.rpc.AlreadyExistsException
import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.pubsub.v1.Topic
import com.google.pubsub.v1.TopicName
import dev.knarusawa.dddDemo.util.logger

// @Component
class PubSubInitializer(
  private val topicAdminClient: TopicAdminClient,
) {
  companion object {
    private val log = logger()
  }

  private val topicIds = listOf("test-topic")

  //  @PostConstruct
  fun initialize() {
    topicIds.forEach { topicId ->
      createTopicIfNotExists(topicId)
    }
    log.info("Pub/Subのtopicの作成が完了.")
  }

  private fun createTopicIfNotExists(topicId: String) {
    try {
      val topic =
        Topic
          .newBuilder()
          .setName(TopicName.ofProjectTopicName("ddd-demo", topicId).toString())
          .build()
      topicAdminClient.createTopic(topic)
      log.info("Topic '$topicId' created successfully.")
    } catch (e: AlreadyExistsException) {
      log.info("Topic '$topicId' already exists. No action taken.")
    }
  }
}
