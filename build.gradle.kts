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
  runtimeOnly(libs.postgresql)
  implementation(libs.spring.boot.starter.data.jdbc)
  implementation(libs.spring.boot.starter.data.jpa)
  implementation(libs.spring.boot.starter.web)
  implementation(libs.spring.boot.starter.webflux)
  implementation(libs.spring.boot.starter.aop)
  implementation(libs.spring.security.crypto)
  implementation(libs.bouncycastle.prov)
  implementation(libs.java.jwt)
  implementation(libs.jakarta.mail.api)
  runtimeOnly(libs.angus.mail)
  implementation(libs.kotlin.result)

  implementation(libs.jackson.module.kotlin)
  implementation(libs.kotlin.reflect)
  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.reactive)
  implementation(libs.kotlinx.coroutines.reactor)
  implementation(libs.jackson.datatype.jsr310)
  implementation(libs.kurrentdb.client)

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
  url = "jdbc:postgresql://localhost:5432/ddd"
  driver = "org.postgresql.Driver"
  user = "user"
  password = "password"
  locations = arrayOf("filesystem:src/main/resources/db/migration")
  cleanDisabled = false
}
