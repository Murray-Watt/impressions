package org.mwatt.base.util.liftweb

import net.liftweb.json.JsonAST.JValue
import net.liftweb.json._

import scala.reflect.ClassTag

/*
REDO: TEMPORARY
 */

class DoubleValueSerializer[T]( toValue:(Double)=>T, fromValue:(T)=>Double )(implicit classTag: ClassTag[T]) extends Serializer[T] {
  private val clazz = classTag.runtimeClass

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), T] = {
    case (TypeInfo(t, _), json) if t == clazz => json match {
      case JDouble(value) => toValue(value)
      case JInt(value) => toValue(value.doubleValue())
      case x => throw new MappingException("Can't convert " + x + " to " + clazz.getCanonicalName)
    }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case x: T =>
      JDouble(fromValue(x))
  }
}
