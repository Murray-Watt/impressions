package org.mwatt.base.util

import scala.language.experimental.macros

class EnumWithName(val name: String, val pretty: Boolean = true) extends Serializable

trait EnumBase[T <: EnumWithName] {
  val values: Seq[T]

  lazy val lookupDict = {
    values.map { v => {
      v.name -> v
    }}.toMap
  }

  def valueOf(name: String) = {
    lookupDict.getOrElse(name, throw new RuntimeException(s"${this.getClass.getCanonicalName}: found no value found for ${name}"))
  }

  def valueOfOption(name: String): Option[T] = {
    lookupDict.get(name)
  }
}



