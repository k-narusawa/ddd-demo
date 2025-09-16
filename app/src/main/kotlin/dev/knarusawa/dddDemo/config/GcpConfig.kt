package dev.knarusawa.dddDemo.config

import com.google.api.gax.core.NoCredentialsProvider
import com.google.api.gax.grpc.GrpcTransportChannel
import com.google.api.gax.rpc.FixedTransportChannelProvider
import com.google.cloud.pubsub.v1.SubscriptionAdminClient
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings
import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.cloud.pubsub.v1.TopicAdminSettings
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class GcpConfig {
  companion object {
    private val noCredentialProvider = NoCredentialsProvider.create()
  }

  @Bean
  @Profile("local")
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
        .setCredentialsProvider(noCredentialProvider)
        .build(),
    )
  }

  @Bean
  @Profile("local")
  fun localSubscriptionAdminClient(
    @Value($$"${spring.cloud.gcp.pubsub.emulator-host}")
    emulatorHost: String,
  ): SubscriptionAdminClient {
    val channel = ManagedChannelBuilder.forTarget(emulatorHost).usePlaintext().build()
    val channelProvider = FixedTransportChannelProvider.create(GrpcTransportChannel.create(channel))

    return SubscriptionAdminClient.create(
      SubscriptionAdminSettings
        .newBuilder()
        .setTransportChannelProvider(channelProvider)
        .setCredentialsProvider(noCredentialProvider)
        .build(),
    )
  }
}
