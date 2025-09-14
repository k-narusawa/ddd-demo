package dev.knarusawa.dddDemo.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class TestDataSourceConfig {
  @Bean
  fun identityAccessJdbcTemplate(
    @Qualifier("identityAccessDataSource") dataSource: DataSource,
  ): JdbcTemplate = JdbcTemplate(dataSource)

  @Bean
  fun projectJdbcTemplate(
    @Qualifier("projectDataSource") dataSource: DataSource,
  ): JdbcTemplate = JdbcTemplate(dataSource)
}
