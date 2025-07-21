package dev.k_narusawa.ddd_demo.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T : Any> T.logger(): Logger {
  return LoggerFactory.getLogger(T::class.java)
}
