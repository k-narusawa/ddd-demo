package dev.knarusawa.dddDemo.config

import com.google.api.gax.grpc.GrpcTransportChannel
import com.google.api.gax.rpc.FixedTransportChannelProvider
import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.cloud.pubsub.v1.TopicAdminSettings
import com.google.cloud.spring.pubsub.core.PubSubTemplate
import com.google.cloud.spring.pubsub.integration.AckMode
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.MessageChannel

class GcpConfig {
  fun inboundChannelAdapter(
    @Qualifier("inputMessageChannel")
    messageChannel: MessageChannel,
    pubSubTemplate: PubSubTemplate,
  ): PubSubInboundChannelAdapter {
    val adapter =
      PubSubInboundChannelAdapter(pubSubTemplate, "sub-one")
    adapter.setOutputChannel(messageChannel)
    adapter.ackMode = AckMode.MANUAL
    adapter.payloadType = String::class.java
    return adapter
  }

  fun topicAdminClient(): TopicAdminClient = TopicAdminClient.create()

  fun localTopicAdminClient(
    @Value($$"${spring.cloud.gcp.pubsub.emulator-host}")
    emulatorHost: String,
  ): TopicAdminClient {
    val channel = ManagedChannelBuilder.forTarget(emulatorHost).usePlaintext().build()
    val channelProvider = FixedTransportChannelProvider.create(GrpcTransportChannel.create(channel))

    return TopicAdminClient.create(
      TopicAdminSettings
        .newBuilder()
        .setTransportChannelProvider(channelProvider)
        .build(),
    )
  }
}
