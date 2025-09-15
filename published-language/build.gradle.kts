import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.proto

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.protobuf)
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(libs.protobuf.kotlin)
}

kotlin {
  jvmToolchain(21)
}

tasks.withType<Copy> {
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

protobuf {
  protoc {
    artifact = "com.google.protobuf:protoc:4.32.1"
  }
  plugins {
    id("grpc") {
      artifact = "io.grpc:protoc-gen-grpc-java:1.75.0"
    }
    id("grpckt") {
      artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.3:jdk8@jar"
    }
  }
  generateProtoTasks {
    all().forEach {
      it.plugins {
        id("grpc")
        id("grpckt")
      }
      it.builtins {
        id("kotlin")
      }
    }
  }
}

sourceSets {
  main {
    proto {
      srcDir("src/main/proto")
    }
  }
}
