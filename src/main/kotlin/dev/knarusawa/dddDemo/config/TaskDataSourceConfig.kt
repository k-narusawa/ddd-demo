package dev.knarusawa.dddDemo.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.knarusawa.dddDemo.config.properties.TaskDatasourceProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
  basePackages = ["dev.knarusawa.dddDemo.app.task"],
  entityManagerFactoryRef = "taskEntityManagerFactory",
  transactionManagerRef = "taskTransactionManager",
)
@EnableConfigurationProperties(TaskDatasourceProperties::class)
class TaskDataSourceConfig {
  @Bean
  @Primary
  fun taskDataSource(properties: TaskDatasourceProperties): DataSource {
    val hikariConfig = HikariConfig()
    hikariConfig.apply {
      jdbcUrl = properties.url
      username = properties.username
      password = properties.password
      connectionTestQuery = "SELECT 1"
      minimumIdle = 5
      maximumPoolSize = 10
    }
    return HikariDataSource(hikariConfig)
  }

  @Bean
  @Primary
  fun taskEntityManagerFactory(
    builder: EntityManagerFactoryBuilder,
    @Qualifier("taskDataSource") dataSource: DataSource,
  ): LocalContainerEntityManagerFactoryBean =
    builder
      .dataSource(dataSource)
      .packages("dev.knarusawa.dddDemo.app.task")
      .persistenceUnit("task")
      .build()

  @Bean
  @Primary
  fun taskTransactionManager(
    @Qualifier("taskEntityManagerFactory") entityManagerFactory:
      LocalContainerEntityManagerFactoryBean,
  ): PlatformTransactionManager = JpaTransactionManager(entityManagerFactory.`object`!!)
}
