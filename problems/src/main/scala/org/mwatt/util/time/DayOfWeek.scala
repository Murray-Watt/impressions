package org.mwatt.util.time

import io.circe.{Decoder, Encoder}
import org.joda.time.DateTimeConstants
import org.mwatt.util.JsonCodecsEnumWithNameHelper.{enumWithNameDecoder, enumWithNameEncoder}
import org.mwatt.util.{EnumBase, EnumHelper, EnumWithName}


sealed abstract class DayOfWeek(name: String, val jodaValue: Int) extends EnumWithName(name)

object DayOfWeek extends EnumBase[DayOfWeek] {
  //noinspection ScalaWeakerAccess
  case object Monday extends DayOfWeek("monday", DateTimeConstants.MONDAY)

  case object Tuesday extends DayOfWeek("tuesday", DateTimeConstants.TUESDAY)

  case object Wednesday extends DayOfWeek("wednesday", DateTimeConstants.WEDNESDAY)

  case object Thursday extends DayOfWeek("thursday", DateTimeConstants.THURSDAY)

  case object Friday extends DayOfWeek("friday", DateTimeConstants.FRIDAY)

  case object Saturday extends DayOfWeek("saturday", DateTimeConstants.SATURDAY)

  case object Sunday extends DayOfWeek("sunday", DateTimeConstants.SUNDAY)

  val DEFAULT_START_OF_WEEK: DayOfWeek = Monday

  // TODO: override lazy val values: Seq[DayOfWeek] = EnumHelper.allValues[DayOfWeek]

  implicit final lazy val decoder: Decoder[DayOfWeek] = enumWithNameDecoder[DayOfWeek](DayOfWeek.values)
  implicit final val encoder: Encoder[DayOfWeek] = enumWithNameEncoder[DayOfWeek]()

  def fromJodaValue(jodaValue: Int): Option[DayOfWeek] = {
    values.find(_.jodaValue == jodaValue)
  }

  override val values: Seq[DayOfWeek] = ???
}
