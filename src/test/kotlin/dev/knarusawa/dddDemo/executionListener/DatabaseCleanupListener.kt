package dev.knarusawa.dddDemo.executionListener

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener

class DatabaseCleanupListener : AbstractTestExecutionListener() {
  companion object {
    private val targetTables =
      listOf(
        "ddd_user",
        "ddd_login_attempt",
        "ddd_activity_log",
        "ddd_token",
        "ddd_actor",
        "ddd_task",
        "ddd_task_event",
        "ddd_team",
      )
  }

  override fun beforeTestMethod(testContext: TestContext) {
    val applicationContext = testContext.applicationContext
    val jdbcTemplate = applicationContext.getBean(JdbcTemplate::class.java)

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
