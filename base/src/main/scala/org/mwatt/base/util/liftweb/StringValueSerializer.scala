package org.mwatt.base.util.liftweb

import net.liftweb.json.JsonAST.{JString, JValue}
import net.liftweb.json._

import scala.reflect.ClassTag

/*
REDO: TEMPORARY
 */

class StringValueSerializer[T]( toValue:(String)=>T, fromValue:(T)=>String )(implicit classTag: ClassTag[T]) extends Serializer[T] {
  private val TheClass = classTag.runtimeClass

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), T] = {
    case (TypeInfo(TheClass, _), json) => json match {
      case JString(value) => toValue(value)
      case x => throw new MappingException("Can't convert " + x + " to " + TheClass.getCanonicalName)
    }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case x: T =>
      JString(fromValue(x))
  }
}
