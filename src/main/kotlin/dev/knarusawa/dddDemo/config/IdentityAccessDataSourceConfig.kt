package dev.knarusawa.dddDemo.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.knarusawa.dddDemo.config.properties.IdentityAccessDatasourceProperties
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
  basePackages = ["dev.knarusawa.dddDemo.app.identityAccess"],
  entityManagerFactoryRef = "identityAccessEntityManagerFactory",
  transactionManagerRef = "identityAccessTransactionManager",
)
@EnableConfigurationProperties(IdentityAccessDatasourceProperties::class)
class IdentityAccessDataSourceConfig {
  @Bean
  @Primary
  fun identityAccessDataSource(properties: IdentityAccessDatasourceProperties): DataSource {
    val hikariConfig = HikariConfig()
    hikariConfig.apply {
      jdbcUrl = properties.url
      username = properties.username
      password = properties.password
    }

    return HikariDataSource(hikariConfig)
  }

  @Bean
  @Primary
  fun identityAccessEntityManagerFactory(
    builder: EntityManagerFactoryBuilder,
    @Qualifier("identityAccessDataSource") dataSource: DataSource,
  ): LocalContainerEntityManagerFactoryBean =
    builder
      .dataSource(dataSource)
      .packages("dev.knarusawa.dddDemo.app.identityAccess")
      .persistenceUnit("identityAccess")
      .build()

  @Bean
  @Primary
  fun identityAccessTransactionManager(
    @Qualifier("identityAccessEntityManagerFactory") entityManagerFactory:
      LocalContainerEntityManagerFactoryBean,
  ): PlatformTransactionManager = JpaTransactionManager(entityManagerFactory.`object`!!)
}
