rootProject.name = "ddd-demo"
include("app")

pluginManagement {
  repositories {
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
    gradlePluginPortal()
  }
}
