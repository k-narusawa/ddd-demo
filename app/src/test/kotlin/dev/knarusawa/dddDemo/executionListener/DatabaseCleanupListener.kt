package dev.knarusawa.dddDemo.executionListener

import dev.knarusawa.dddDemo.util.logger
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener

class DatabaseCleanupListener : AbstractTestExecutionListener() {
  companion object {
    private val identityAccessTables =
      listOf(
        "ddd_user",
        "ddd_activity_log",
        "ddd_token",
        "ddd_outbox",
      )
    private val projectTables =
      listOf(
        "ddd_member",
        "ddd_member_role",
        "ddd_project",
        "ddd_event",
        "ddd_task_read_model",
      )

    private val log = logger()
  }

  override fun beforeTestMethod(testContext: TestContext) {
    val applicationContext = testContext.applicationContext
    val identityAccessJdbcTemplate =
      applicationContext.getBean("identityAccessJdbcTemplate", JdbcTemplate::class.java)
    val projectJdbcTemplate =
      applicationContext.getBean("projectJdbcTemplate", JdbcTemplate::class.java)

    cleanupDatabase(identityAccessJdbcTemplate, identityAccessTables)
    cleanupDatabase(projectJdbcTemplate, projectTables)

    log.info("データベースクリーニング完了: ${testContext.testMethod.name}")

    super.beforeTestMethod(testContext)
  }

  private fun cleanupDatabase(
    jdbcTemplate: JdbcTemplate,
    tables: List<String>,
  ) {
    jdbcTemplate.execute("SET session_replication_role = 'replica';") // 外部キー制約を外す

    tables.forEach { tableName ->
      try {
        jdbcTemplate.execute("DELETE FROM $tableName")
      } catch (e: Exception) {
        System.err.println("Failed to clean table $tableName: ${e.message}")
      }
    }
    jdbcTemplate.execute("SET session_replication_role = 'origin';")
  }
}
