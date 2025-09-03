plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
  alias(libs.plugins.kotlin.jpa)
  alias(libs.plugins.flyway)
  alias(libs.plugins.ktlint)
  kotlin("plugin.serialization") version "2.2.0"
}

group = "dev.k-narusawa"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

repositories {
  mavenCentral()
  maven { url = uri("https://repo.spring.io/milestone") }
  maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
  implementation(libs.flyway.core)
  implementation(libs.flyway.database.postgresql)
  implementation(libs.postgresql)
  implementation(libs.spring.boot.starter.data.jdbc)
  implementation(libs.spring.boot.starter.data.jpa)
  implementation(libs.spring.boot.starter.webflux)
  implementation(libs.spring.boot.starter.aop)
  implementation(libs.spring.security.crypto)
  implementation(libs.spring.integration.core)
  implementation(libs.spring.cloud.gcp.dependencies)
  implementation(libs.spring.cloud.gcp.starter.pubsub)
  implementation(libs.bouncycastle.prov)
  implementation(libs.java.jwt)
  implementation(libs.jakarta.mail.api)
  runtimeOnly(libs.angus.mail)
  implementation(libs.kotlin.result)
  implementation(libs.context.propagation)

  implementation(libs.jackson.module.kotlin)
  implementation(libs.kotlin.reflect)
  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.reactive)
  implementation(libs.kotlinx.coroutines.reactor)
  implementation(libs.jackson.datatype.jsr310)

  runtimeOnly(libs.flyway.core)
  runtimeOnly(libs.flyway.database.postgresql)

  testImplementation(libs.spring.boot.starter.test)
  testImplementation(libs.kotlin.test.junit5)
  testImplementation(libs.spring.security.test)
  testRuntimeOnly(libs.junit.platform.launcher)
  testImplementation(libs.mockk)
}

buildscript {
  dependencies {
    classpath(libs.flyway.database.postgresql)
  }
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
  }
}

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

flyway {
  driver = "org.postgresql.Driver"
  cleanDisabled = false
}

tasks.register("flywayMigrateIdentityAccess", org.flywaydb.gradle.task.FlywayMigrateTask::class) {
  url = System.getenv("IDENTITY_ACCESS_DATASOURCE_URL")
    ?: "jdbc:postgresql://localhost:5432/ddd_identity_access"
  user = System.getenv("IDENTITY_ACCESS_DATASOURCE_USERNAME") ?: "user"
  password = System.getenv("IDENTITY_ACCESS_DATASOURCE_PASSWORD") ?: "password"
  locations = arrayOf("filesystem:src/main/resources/db/migration/identityAccess")
  baselineOnMigrate = true
}

tasks.register("flywayMigrateTask", org.flywaydb.gradle.task.FlywayMigrateTask::class) {
  url = System.getenv("TASK_DATASOURCE_URL") ?: "jdbc:postgresql://localhost:5432/ddd_task"
  user = System.getenv("TASK_DATASOURCE_USERNAME") ?: "user"
  password = System.getenv("TASK_DATASOURCE_PASSWORD") ?: "password"
  locations = arrayOf("filesystem:src/main/resources/db/migration/task")
  baselineOnMigrate = true
}

tasks.named("flywayMigrate") {
  dependsOn(tasks.named("flywayMigrateIdentityAccess"), tasks.named("flywayMigrateTask"))
  enabled = false
}

tasks.register("flywayCleanIdentityAccess", org.flywaydb.gradle.task.FlywayCleanTask::class) {
  url = System.getenv("IDENTITY_ACCESS_DATASOURCE_URL")
    ?: "jdbc:postgresql://localhost:5432/ddd_identity_access"
  user = System.getenv("IDENTITY_ACCESS_DATASOURCE_USERNAME") ?: "user"
  password = System.getenv("IDENTITY_ACCESS_DATASOURCE_PASSWORD") ?: "password"
}

tasks.register("flywayCleanTask", org.flywaydb.gradle.task.FlywayCleanTask::class) {
  url = System.getenv("TASK_DATASOURCE_URL") ?: "jdbc:postgresql://localhost:5432/ddd_task"
  user = System.getenv("TASK_DATASOURCE_USERNAME") ?: "user"
  password = System.getenv("TASK_DATASOURCE_PASSWORD") ?: "password"
}

tasks.named("flywayClean") {
  dependsOn(tasks.named("flywayCleanIdentityAccess"), tasks.named("flywayCleanTask"))
  enabled = false
}
