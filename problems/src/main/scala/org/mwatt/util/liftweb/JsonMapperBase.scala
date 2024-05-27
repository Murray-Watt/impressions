package org.mwatt.util.liftweb

import net.liftweb.json.Serialization.{read, write, writePretty}
import net.liftweb.json.{Serialization, Serializer, ShortTypeHints}

/*
REDO: TEMPORARY
 */

class JsonMapperBase( val serializers:Seq[Serializer[_]] = Seq(),
                      val classesForTypeHints:Seq[Class[_]] = Seq()) {

  lazy implicit val formats = Serialization.formats(ShortTypeHints(classesForTypeHints.toList)) ++ serializers

  def toJson(obj:AnyRef, pretty: Boolean = false): String = {
    if (pretty) {
      writePretty(obj)
    } else {
      write(obj)
    }
  }

  def fromJson[T](json:String)(implicit manifest: Manifest[T]):T = {
    try {
      read[T](json)
    } catch {
      case e: Exception =>
        throw new JsonParseException(s"unable to parse json: ${json}", e)
    }
  }

  def ++(other:JsonMapperBase) = {
    new JsonMapperBase(serializers ++ other.serializers, classesForTypeHints ++ other.classesForTypeHints)
  }
}
class JsonParseException(message: String, cause: Throwable) extends RuntimeException(message, cause)
