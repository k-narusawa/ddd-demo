package dev.knarusawa.dddDemo.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "spring.datasource.task")
data class TaskDatasourceProperties
  @ConstructorBinding
  constructor(
    val url: String,
    val username: String,
    val password: String,
  )
