plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
  alias(libs.plugins.kotlin.jpa)
  alias(libs.plugins.flyway)
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
  implementation(libs.spring.boot.starter.kafka)
  testImplementation("org.springframework.kafka:spring-kafka-test")
  runtimeOnly(libs.postgresql)
  implementation(libs.spring.boot.starter.data.jdbc)
  implementation(libs.spring.boot.starter.data.jpa)
  implementation(libs.spring.boot.starter.web)
  implementation(libs.spring.security.crypto)
  implementation(libs.bouncycastle.prov)
  implementation(libs.java.jwt)
  implementation(libs.jakarta.mail.api)
  runtimeOnly(libs.angus.mail)

  implementation(libs.jackson.module.kotlin)
  implementation(libs.kotlin.reflect)
  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.kotlinx.coroutines.reactive)
  implementation(libs.kotlinx.coroutines.reactor)

  runtimeOnly(libs.flyway.core)
  runtimeOnly(libs.flyway.database.postgresql)

  testImplementation(libs.spring.boot.starter.test)
  testImplementation(libs.spring.boot.starter.kafka)
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
