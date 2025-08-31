package dev.knarusawa.dddDemo.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

class DataSourceConfig {
  @Bean
  fun dataSource(properties: DataSourceProperties): DataSource {
    val builder =
      properties
        .initializeDataSourceBuilder()
        .type(HikariDataSource::class.java)

    return builder.build()
  }
}
