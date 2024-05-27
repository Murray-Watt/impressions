package org.mwatt.util.time

import io.circe.Decoder.Result
import io.circe.{Encoder, _}
import org.joda.time.{DateTime, DateTimeZone, LocalDate, LocalTime, YearMonth}

trait TimeJsonCodecs {
  implicit final val timeZoneEncoder: Encoder[DateTimeZone] = new Encoder[DateTimeZone] {
    override def apply(a: DateTimeZone) = Encoder.encodeString(a.getID)
  }

  implicit final val timeZoneDecoder: Decoder[DateTimeZone] = new Decoder[DateTimeZone] {
    override def apply(c: HCursor): Result[DateTimeZone] = c.as[String].right.map(DateTimeZone.forID)
  }

  implicit final val dateTimeEncoder: Encoder[DateTime] = new Encoder[DateTime] {
    override def apply(a: DateTime): Json = Encoder.encodeString(DateTimeFormats.IsoDateWithTimeFormatterUtc.formatDateTime(a))
  }

  implicit final val dateTimeDecoder: Decoder[DateTime] = new Decoder[DateTime] {
    override def apply(c: HCursor): Result[DateTime] = c.as[String].right.map(DateTimeFormats.IsoDateWithTimeFormatterUtc.parseDateRetainZone)
  }

  implicit final val localDateEncoder: Encoder[LocalDate] = new Encoder[LocalDate] {
    override def apply(a: LocalDate): Json = Encoder.encodeString(DateTimeFormats.CalendarDateFormatter.formatLocalDate(a))
  }

  implicit final val localDateDecoder: Decoder[LocalDate] = new Decoder[LocalDate] {
    override def apply(c: HCursor): Result[LocalDate] = c.as[String].right.map(DateTimeFormats.CalendarDateFormatter.parseDateAsLocalDate)
  }

  implicit final val localTimeEncoder: Encoder[LocalTime] =
    Encoder.forProduct4("hour", "minute", "second", "millis")(a => (a.getHourOfDay, a.getMinuteOfHour, a.getSecondOfMinute, a.getMillisOfSecond))

  implicit final val localTimeDecoder: Decoder[LocalTime] =
    Decoder.forProduct4[LocalTime, Int, Int, Int, Int]("hour", "minute", "second", "millis")((a, b, c, d) => new LocalTime(a, b, c, d))

  implicit final val yearMonthEncoder: Encoder[YearMonth] = new Encoder[YearMonth] {
    override def apply(a: YearMonth): Json = Encoder.encodeString(DateTimeFormats.YearMonthFormatter.formatYearMonth(a))
  }

  implicit final val yearMonthDecoder: Decoder[YearMonth] = new Decoder[YearMonth] {
    override def apply(c: HCursor): Result[YearMonth] = c.as[String].right.map(DateTimeFormats.YearMonthFormatter.parseDateAsYearMonth)
  }

}

object TimeJsonCodecs {
  def getDateTimeEncoder(dateTimeFormatter: DateTimeFormatter): Encoder[DateTime] = {
    (a: DateTime) => Encoder.encodeString(dateTimeFormatter.formatDateTime(a))
  }

  def getDateTimeDecoder(dateTimeFormatter: DateTimeFormatter, xForm: DateTime => DateTime = identity): Decoder[DateTime] = {
    (c: HCursor) => c.as[String].right.map(dateTimeFormatter.parseDateRetainZone).map(xForm)
  }
}


trait DateTimeWithoutMillisJsonCodecs {
  implicit final val dateTimeEncoder: Encoder[DateTime] = new Encoder[DateTime] {
    override def apply(a: DateTime): Json = Encoder.encodeString(DateTimeFormats.IsoDateWithTimeAndZoneOffsetFormatter.formatDateTime(a))
  }

  implicit final val dateTimeDecoder: Decoder[DateTime] = new Decoder[DateTime] {
    override def apply(c: HCursor): Result[DateTime] = c.as[String].right.map(DateTimeFormats.IsoDateWithTimeAndZoneOffsetFormatter.parseDateRetainZone)
  }
}