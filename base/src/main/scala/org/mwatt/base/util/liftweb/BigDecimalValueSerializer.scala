package org.mwatt.base.util.liftweb

import net.liftweb.json.JsonAST.JValue
import net.liftweb.json._

/*
REDO: TEMPORARY
 */

class BigDecimalValueSerializer extends Serializer[BigDecimal] {
  private val TheClass = classOf[BigDecimal]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), BigDecimal] = {
    case (TypeInfo(TheClass, _), json) => json match {
      case JDouble(value) => BigDecimal(value)
      case JInt(value) => value.doubleValue()
      case x => throw new MappingException("Can't convert " + x + " to " + TheClass.getCanonicalName)
    }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case x: BigDecimal =>
      JDouble(x.toDouble)
  }
}
