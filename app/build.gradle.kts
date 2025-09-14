plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
  alias(libs.plugins.kotlin.jpa)
  alias(libs.plugins.flyway)
  alias(libs.plugins.ktlint)
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

  implementation(libs.kotlin.reflect)
  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.reactive)
  implementation(libs.kotlinx.coroutines.reactor)
  implementation(libs.kotlinx.serialization.json)

  implementation(libs.java.jwt)
  implementation(libs.jackson.module.kotlin)
  implementation(libs.jackson.datatype.jsr310)
  implementation(libs.jakarta.mail.api)
  implementation(libs.bouncycastle.prov)
  runtimeOnly(libs.angus.mail)
  implementation(libs.kotlin.result)
  implementation(libs.context.propagation)

  implementation(project(":published-language"))

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

ktlint {
  filter {
    exclude("**/generated/**")
    include("**/kotlin/**")
  }
}

flyway {
  driver = "org.postgresql.Driver"
  cleanDisabled = false
}

tasks.withType<Test> {
  systemProperty("spring.profiles.active", "test")
}

tasks.register("flywayMigrateIdentityAccess", org.flywaydb.gradle.task.FlywayMigrateTask::class) {
  url = System.getenv("IDENTITY_ACCESS_DATASOURCE_URL")
    ?: "jdbc:postgresql://localhost:5432/ddd_identity_access"
  user = System.getenv("IDENTITY_ACCESS_DATASOURCE_USERNAME") ?: "user"
  password = System.getenv("IDENTITY_ACCESS_DATASOURCE_PASSWORD") ?: "password"
  locations = arrayOf("filesystem:src/main/resources/db/migration/identityAccess")
  baselineOnMigrate = true
}

tasks.register("flywayMigrateProject", org.flywaydb.gradle.task.FlywayMigrateTask::class) {
  url = System.getenv("PROJECT_DATASOURCE_URL") ?: "jdbc:postgresql://localhost:5432/ddd_project"
  user = System.getenv("PROJECT_DATASOURCE_USERNAME") ?: "user"
  password = System.getenv("PROJECT_DATASOURCE_PASSWORD") ?: "password"
  locations = arrayOf("filesystem:src/main/resources/db/migration/project")
  baselineOnMigrate = true
}

tasks.named("flywayMigrate") {
  dependsOn(tasks.named("flywayMigrateIdentityAccess"), tasks.named("flywayMigrateProject"))
  enabled = false
}

tasks.register("flywayCleanIdentityAccess", org.flywaydb.gradle.task.FlywayCleanTask::class) {
  url = System.getenv("IDENTITY_ACCESS_DATASOURCE_URL")
    ?: "jdbc:postgresql://localhost:5432/ddd_identity_access"
  user = System.getenv("IDENTITY_ACCESS_DATASOURCE_USERNAME") ?: "user"
  password = System.getenv("IDENTITY_ACCESS_DATASOURCE_PASSWORD") ?: "password"
}

tasks.register("flywayCleanProject", org.flywaydb.gradle.task.FlywayCleanTask::class) {
  url = System.getenv("PROJECT_DATASOURCE_URL") ?: "jdbc:postgresql://localhost:5432/ddd_project"
  user = System.getenv("PROJECT_DATASOURCE_USERNAME") ?: "user"
  password = System.getenv("PROJECT_DATASOURCE_PASSWORD") ?: "password"
}

tasks.named("flywayClean") {
  dependsOn(tasks.named("flywayCleanIdentityAccess"), tasks.named("flywayCleanProject"))
  enabled = false
}
