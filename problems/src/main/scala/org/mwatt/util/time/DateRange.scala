package org.mwatt.util.time


import io.circe.derivation.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import org.joda.time.{DateTime, DateTimeZone, Days, LocalDate}

case class DateInclusive(value: DateTime)
case class DateExclusive(value: DateTime)

case class DateRange(startInclusive: LocalDate, endInclusive: LocalDate, clientTimeZone: DateTimeZone) {

  require(startInclusive.isBefore(endInclusive) || startInclusive == endInclusive, s"start date ${startInclusive} must be before or equal to end date ${endInclusive}")

  private val startOfStartDay = startInclusive.toDateTimeAtStartOfDay(clientTimeZone)
  private val dayAfterEndDay = endInclusive.toDateTimeAtStartOfDay(clientTimeZone).plusDays(1)

  lazy val utcStartDateInclusive: DateInclusive = DateInclusive(startOfStartDay.withZone(TimeZones.UTC_ZONE))
  lazy val utcEndDateInclusive: DateInclusive = DateInclusive(dayAfterEndDay.withZone(TimeZones.UTC_ZONE).minusMillis(1))
  lazy val utcEndDateExclusive: DateExclusive = DateExclusive(dayAfterEndDay.withZone(TimeZones.UTC_ZONE))

  lazy val clientStartDateInclusive: DateInclusive = DateInclusive(startOfStartDay)

  lazy val clientEndDateInclusive: DateInclusive = DateInclusive(dayAfterEndDay.minusMillis(1))
  lazy val clientEndDateExclusive: DateExclusive = DateExclusive(dayAfterEndDay)

  override def equals(obj: Any): Boolean = {
    if (!obj.isInstanceOf[DateRange]) return false
    val other = obj.asInstanceOf[DateRange]
    startInclusive == other.startInclusive && endInclusive == other.endInclusive && clientTimeZone == other.clientTimeZone
  }

  lazy val daysBetween = Days.daysBetween(clientStartDateInclusive.value, clientEndDateExclusive.value).getDays

  override def toString: String = {
    s"${startInclusive} to ${endInclusive} @ ${clientTimeZone}"
  }

  def getDateString(formatter: DateTimeFormatter): String = {
    val start = clientStartDateInclusive.value.toLocalDate
    val end = clientEndDateInclusive.value.toLocalDate

    if (start == end) formatter.formatLocalDate(start).toUpperCase()
    else {
      formatter.formatLocalDate(start).toUpperCase + " to " + formatter.formatLocalDate(end).toUpperCase()
    }
  }

}

object DateRange extends TimeJsonCodecs {
  implicit final lazy val decoder: Decoder[DateRange] = deriveDecoder[DateRange]
  implicit final val encoder: Encoder[DateRange] = deriveEncoder[DateRange]
}

class UtcDateRange(startInclusive: LocalDate, endInclusive: LocalDate) extends DateRange(startInclusive, endInclusive, TimeZones.UTC_ZONE)
