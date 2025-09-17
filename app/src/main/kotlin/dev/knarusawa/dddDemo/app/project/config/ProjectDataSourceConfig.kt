package dev.knarusawa.dddDemo.app.project.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.knarusawa.dddDemo.app.project.config.properties.ProjectDatasourceProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
  basePackages = [
    "dev.knarusawa.dddDemo.app.project.adapter.gateway.db",
    "dev.knarusawa.dddDemo.app.project.application",
    "dev.knarusawa.dddDemo.app.project.domain",
  ],
  entityManagerFactoryRef = "projectEntityManagerFactory",
  transactionManagerRef = "projectTransactionManager",
)
@EnableConfigurationProperties(ProjectDatasourceProperties::class)
class ProjectDataSourceConfig {
  @Bean
  fun projectDataSource(properties: ProjectDatasourceProperties): DataSource {
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
  fun projectEntityManagerFactory(
    builder: EntityManagerFactoryBuilder,
    @Qualifier("projectDataSource") dataSource: DataSource,
  ): LocalContainerEntityManagerFactoryBean =
    builder
      .dataSource(dataSource)
      .packages("dev.knarusawa.dddDemo.app.project")
      .persistenceUnit("project")
      .build()

  @Bean
  fun projectTransactionManager(
    @Qualifier("projectEntityManagerFactory")
    entityManagerFactory: LocalContainerEntityManagerFactoryBean,
  ): PlatformTransactionManager = JpaTransactionManager(entityManagerFactory.`object`!!)
}
