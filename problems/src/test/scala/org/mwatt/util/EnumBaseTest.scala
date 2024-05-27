package org.mwatt.util

import io.circe.{Decoder, Encoder}
import org.mwatt.base.util.{EnumBase, EnumHelper, EnumWithName, JsonUtil}
import org.mwatt.base.util.JsonCodecsEnumWithNameHelper.{enumWithNameDecoder, enumWithNameEncoder}
import org.scalatest.FunSuite

class EnumBaseTest extends FunSuite {

  test("color serialization") {
    val color = Color.Red
  }

}

sealed abstract class Color(name: String) extends EnumWithName(name)


object Color extends EnumBase[Color] {
  case object Red extends Color("Red")
  case object Green extends Color("Green")
  case object Blue extends Color("Blue")

  override lazy val values: Seq[Color] = EnumHelper.allValues[Color]
  implicit final lazy val budgetTypeDecoder: Decoder[Color] = enumWithNameDecoder[Color](Color.values)
  implicit final val budgetTypeEncoder: Encoder[Color] = enumWithNameEncoder[Color]()
}
