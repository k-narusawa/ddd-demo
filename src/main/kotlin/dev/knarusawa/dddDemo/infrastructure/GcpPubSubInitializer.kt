package dev.knarusawa.dddDemo.infrastructure

import com.google.api.gax.rpc.AlreadyExistsException
import com.google.cloud.pubsub.v1.SubscriptionAdminClient
import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.pubsub.v1.DeadLetterPolicy
import com.google.pubsub.v1.Subscription
import com.google.pubsub.v1.SubscriptionName
import com.google.pubsub.v1.Topic
import com.google.pubsub.v1.TopicName
import dev.knarusawa.dddDemo.util.logger
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class GcpPubSubInitializer(
  private val topicAdminClient: TopicAdminClient,
  private val subscriptionAdminClient: SubscriptionAdminClient,
) {
  companion object {
    private val log = logger()
  }

  @Value($$"${spring.cloud.gcp.project-id}")
  private lateinit var projectName: String

  @PostConstruct
  fun initialize() {
    val pubsubModels = PubSubModel.of()
    pubsubModels.forEach { pubsubModel ->
      createTopicAndSubscriptionIfNotExists(pubSubModel = pubsubModel)
    }
    log.info("Pub/Subの初期設定完了.")
  }

  private fun createTopicAndSubscriptionIfNotExists(pubSubModel: PubSubModel) {
    val topic =
      Topic
        .newBuilder()
        .setName(TopicName.ofProjectTopicName(projectName, pubSubModel.topic).toString())
        .build()

    try {
      topicAdminClient.createTopic(topic)
      log.info("トピックを作成 topic:'${topic.name}' ")
    } catch (e: AlreadyExistsException) {
      log.info("トピックがすでに存在したため作成に失敗 topic:'${topic.name}'")
    } catch (e: Exception) {
      log.error("トピックの作成に失敗 topic:'${topic.name}'", e)
    }

    pubSubModel.subscriptions.forEach { it ->
      val subscription =
        Subscription
          .newBuilder()
          .setTopic(topic.name)
          .setName(SubscriptionName.of(projectName, it).toString())
          .setDeadLetterPolicy(
            DeadLetterPolicy
              .newBuilder()
              .setDeadLetterTopic(topic.name)
              .setMaxDeliveryAttempts(5)
              .build(),
          ).build()
      try {
        subscriptionAdminClient.createSubscription(subscription)
        log.info("サブスクリプションを作成 topic:'${topic.name}' subscription: '${subscription.name}'.")
      } catch (e: AlreadyExistsException) {
        log.warn(
          "サブスクリプションがすでに存在したため作成に失敗 topic:'${topic.name}' subscription: '${subscription.name}'",
        )
      } catch (e: Exception) {
        log.error(
          "サブスクリプションの作成に失敗 topic:'${topic.name}' subscription: '${subscription.name}'",
          e,
        )
      }
    }
  }
}
