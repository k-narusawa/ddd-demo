package dev.knarusawa.dddDemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.integration.annotation.IntegrationComponentScan
import org.springframework.integration.config.EnableIntegration

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
class DddDemoApplication

fun main(args: Array<String>) {
  runApplication<DddDemoApplication>(*args)
}
