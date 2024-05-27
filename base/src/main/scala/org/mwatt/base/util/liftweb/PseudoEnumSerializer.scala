package org.mwatt.base.util.liftweb

import net.liftweb.json._
import org.mwatt.base.util.EnumWithName

import scala.reflect.ClassTag

/*
REDO: TEMPORARY
 */

class PseudoEnumSerializer[T <: EnumWithName](values:Seq[T])(implicit classTag: ClassTag[T]) extends Serializer[T] {
  private val clazz = classTag.runtimeClass

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), T] = {
    case (TypeInfo(t, _), json) if t == clazz => json match {
      case JString(value) => values.find(_.name == value).getOrElse(throw new RuntimeException(s"Unknown value: ${value}"))
      case x => throw new MappingException("Can't convert " + x + " to " + clazz.getCanonicalName)
    }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case x: T =>
      JString(x.name)
  }
}
