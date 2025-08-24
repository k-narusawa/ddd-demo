package dev.k_narusawa.ddd_demo.app.task.adapter.http

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
    @Value("\${services.identity-access.url}")
    private lateinit var identityAccessUrl: String

    @Bean
    fun identityAccessWebClient(): WebClient =
        WebClient
            .builder()
            .baseUrl(identityAccessUrl)
            .build()
}
