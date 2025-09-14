package dev.knarusawa.dddDemo.app.identityAccess.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "spring.datasource.identity-access")
data class IdentityAccessDatasourceProperties
  @ConstructorBinding
  constructor(
    val url: String,
    val username: String,
    val password: String,
  )
