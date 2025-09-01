package dev.knarusawa.dddDemo.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(TaskDatasourceConfig::class)
class DataSourceConfig {
  @Bean
  fun dataSource(taskDatasourceConfig: TaskDatasourceConfig): DataSource {
    val hikariConfig = HikariConfig()
    hikariConfig.apply {
      driverClassName = "org.postgresql.Driver"
      jdbcUrl = taskDatasourceConfig.url
      username = taskDatasourceConfig.username
      password = taskDatasourceConfig.password
      connectionTestQuery = "SELECT 1"
      minimumIdle = 5
      maximumPoolSize = 10
    }

    return HikariDataSource(hikariConfig)
  }
}

@ConfigurationProperties(prefix = "spring.datasource.task")
data class TaskDatasourceConfig
  @ConstructorBinding
  constructor(
    val url: String,
    val username: String,
    val password: String,
  )
