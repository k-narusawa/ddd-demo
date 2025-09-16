plugins {
  alias(libs.plugins.kotlin.jvm)
}

repositories {
  mavenCentral()
  maven { url = uri("https://repo.spring.io/milestone") }
  maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
}

kotlin {
  jvmToolchain(21)
}
