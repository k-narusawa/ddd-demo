package dev.knarusawa.dddDemo.executionListener

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener

class DatabaseCleanupListener : AbstractTestExecutionListener() {
  companion object {
    private val targetTables =
      listOf(
        // identity_access
        "ddd_user",
        "ddd_activity_log",
        "ddd_token",
        // project
        "ddd_member",
        "ddd_member_role",
        "ddd_project",
        "ddd_project_event",
        "ddd_task_outbox",
        "ddd_task_read_model",
      )
  }

  override fun beforeTestMethod(testContext: TestContext) {
    val applicationContext = testContext.applicationContext
    val jdbcTemplate = applicationContext.getBean(JdbcTemplate::class.java)

    jdbcTemplate.execute("use ddd_identity_access;")
    jdbcTemplate.execute("SET session_replication_role = 'replica';") // 外部キー制約を外す

    targetTables.forEach { tableName ->
      try {
        jdbcTemplate.execute("DELETE FROM $tableName")
      } catch (e: Exception) {
        System.err.println("Failed to clean table $tableName: ${e.message}")
      }
    }
    jdbcTemplate.execute("SET session_replication_role = 'origin';")
    println("データベースクリーニング完了: ${testContext.testMethod.name}")

    super.beforeTestMethod(testContext)
  }
}
