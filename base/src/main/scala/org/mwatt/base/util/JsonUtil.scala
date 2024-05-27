package org.mwatt.base.util

import io.circe.Decoder.Result
import io.circe._
import io.circe.parser.parse

/*
REDO: TEMPORARY
  */

object JsonUtil {
  def fromJson[T](json: String)(implicit decoder: Decoder[T]): T = {
    try {
      parse(json) match {
        case Left(err: io.circe.Error) => throw new RuntimeException(s"Error parsing json $json. \n${err.getMessage()}")
        case Right(parsed: Json) => parsed.as[T] match {
          case Left(err) => throw new RuntimeException(s"Error deserializing json $json. \n${err.getMessage()}")
          case Right(result) => result
        }
      }
    } catch {
      case e: Exception => throw new RuntimeException(s"Error parsing json $json", e)
    }
  }

  def toJson[T](obj: T, pretty: Boolean = false)(implicit encoder: Encoder[T]): String = {
    if (pretty) encoder(obj).pretty(prettyPrinter) else encoder(obj).pretty(printer)
  }

  val printer = Printer.noSpaces.copy(dropNullValues = true)
  val prettyPrinter = Printer.spaces2.copy(dropNullValues = true)
}

object JsonCodecsEnumWithNameHelper {
  def enumWithNameEncoder[T <: EnumWithName](): Encoder[T] = {
    new Encoder[T] {
      override def apply(a: T): Json = Encoder.encodeString(a.name)
    }
  }

  def enumWithNameDecoder[T <: EnumWithName](values: Seq[T]): Decoder[T] = {
    new Decoder[T] {
      override def apply(c: HCursor): Result[T] = {
        val cursorResult = c.as[String]
        if (cursorResult.isRight) {
          Right(values.find(_.name == c.as[String].right.get).get)
        } else {
          throw new RuntimeException(s"Enum value was not a string: ${c.value.toString()}. looking in values: ${values.map(_.name)}")
        }
      }
    }
  }
}

object JsonCodecsKeyHelper {
  def keyEncoder[T]()(implicit encoder: Encoder[T]): KeyEncoder[T] = new KeyEncoder[T] {
    override def apply(key: T): String = JsonUtil.toJson(key)
  }

  def keyDecoder[T]()(implicit decoder: Decoder[T]): KeyDecoder[T] = new KeyDecoder[T] {
    override def apply(key: String): Option[T] = Option(JsonUtil.fromJson[T](key))
  }
}

object JsonCodecsEnumKeyHelper {
  def enumKeyEncoder[T <: EnumWithName](): KeyEncoder[T] = new KeyEncoder[T] {
    override def apply(e: T): String = e.name
  }

  def enumKeyDecoder[T <: EnumWithName](values: Seq[T]): KeyDecoder[T] = new KeyDecoder[T] {
    override def apply(key: String): Option[T] = values.find(_.name == key)
  }
}