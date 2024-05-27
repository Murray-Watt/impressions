package org.mwatt.base.util

import org.apache.logging.log4j.{LogManager, Logger}

trait Log4s {
  val logger: Logger = LogManager.getLogger(this.getClass)

  def info(message: String): Unit = logger.info(message)
  def info(message: String, t: Throwable): Unit = logger.info(message,t)

  def warn(message: String): Unit = logger.warn(message)
  def warn(message: String, t: Throwable): Unit = logger.warn(message, t)

  def error(message: String): Unit = logger.error(message)
  def error(message: String, t: Throwable): Unit = logger.error(message, t)
}