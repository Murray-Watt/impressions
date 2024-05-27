package org.mwatt.base.util.time

import org.joda.time._
import org.joda.time.format.DateTimeFormat

/*
REDO: TEMPORARY
 */

object DateTimeFormats {

  val IsoDateWithTimeAndZoneOffsetFormatter = new DateTimeFormatter("yyyy-MM-dd'T'HH:mm:ssZZ")
  val IsoDateWithTimeFormatterUtc = new DateTimeFormatter("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  val IsoDateWithTimeFormatterUtcHigherPrecision = new DateTimeFormatter("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'")
  val IsoDateWithoutTimeDesignator = new DateTimeFormatter("yyyy-MM-dd HH:mm:ss.SSS")

  val DateTimeNoOffsetFormatter = new DateTimeFormatter("yyyy-MM-dd'T'HH:mm:ss")
  val CalendarDateFormatter = new DateTimeFormatter("yyyy-MM-dd")
  val S3HourFormatter = new DateTimeFormatter("yyyy-MM-dd/HH")
  val UIDatePickerFormatter = new DateTimeFormatter("dd-MMM-yyyy")
  val YearMonthFormatter = new DateTimeFormatter("yyyy-MM")
  val MonthDayYearFormatter = new DateTimeFormatter("MM/dd/yyyy")
  val MonthDayYearDotFormatter = new DateTimeFormatter("MM.dd.yyyy")
  val HourMinuteFormatter = DateTimeFormat.forPattern("HH:mm")
}

sealed class DateTimeFormatter(val dateFormat: String) {
  val dateFormatter: format.DateTimeFormatter = DateTimeFormat.forPattern(dateFormat)

  def parseDateAsDateTime(dateString:String, timeZone:DateTimeZone = DateTimeZone.UTC) = DateTime.parse(dateString, dateFormatter.withZone(timeZone))

  def parseDateRetainZone(dateString: String) = {
    val withOffset = DateTime.parse(dateString, dateFormatter.withOffsetParsed())
    val zoneFromDateString = withOffset.getZone
    DateTime.parse(dateString, dateFormatter.withZone(zoneFromDateString))
  }

  def parseDateAsLocalDate(dateString:String) = LocalDate.parse(dateString, dateFormatter)

  def parseDateAsYearMonth(dateString:String) = YearMonth.parse(dateString, dateFormatter)

  def formatDateTime(date: DateTime, timeZone:DateTimeZone = DateTimeZone.UTC) = date.toString(dateFormatter.withZone(timeZone))
  def formatLocalDate(day: LocalDate) = day.toString(dateFormatter)
  def formatYearMonth(ym: YearMonth) = ym.toString(dateFormatter)
}
