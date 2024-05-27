package org.mwatt.util

import org.joda.time._
import org.mwatt.util.time.{DateTimeFormats, TimeZones}

import scala.concurrent.duration.DurationInt

/*
REDO: TEMPORARY
 */

object DateTimeUtil {

  implicit val dateTimeOrdering: Ordering[DateTime] = Ordering.fromLessThan(_ isBefore _)
  implicit val localDateOrdering: Ordering[LocalDate] = Ordering.fromLessThan(_ isBefore _)
  implicit val yearMonthOrdering: Ordering[YearMonth] = Ordering.fromLessThan(_ isBefore _)
  implicit val yearMonthOrderingDesc: Ordering[YearMonth] = Ordering.fromLessThan(_ isAfter _)

  def isSameCalendarMonth(ym: YearMonth, month: YearMonth): Boolean = {
     ym.getYear == month.getYear && ym.getMonthOfYear == month.getMonthOfYear
  }

  def min(dates: DateTime*): DateTime = {
    dates.min
  }
  def min(days: LocalDate*): LocalDate = {
    days.min
  }

  def min(dates: Option[DateTime]*): Option[DateTime] = {
    val flat = dates.flatten
    if(flat.isEmpty) None else Some(flat.min)
  }

  def max(dates: Option[DateTime]*): Option[DateTime] = {
    val flat = dates.flatten
    if(flat.isEmpty) None else Some(flat.max)
  }

  def max(dates: DateTime*): DateTime = {
    dates.max
  }

  def max(dates: LocalDate*): LocalDate = {
    dates.max
  }

  def isTruncatedToHour(dateTime: DateTime): Boolean =
    dateTime.getMinuteOfHour == 0 && dateTime.getSecondOfMinute == 0 && dateTime.getMillisOfSecond == 0

  def truncateToHour(dateTime: DateTime): DateTime = dateTime.withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0)

  def truncateToFirstOfMonth(dateTime: DateTime): DateTime = dateTime.withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0)

  def truncateToHour(localTime: LocalTime): LocalTime = localTime.withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0)

  def truncateToDay(dateTime: DateTime): DateTime = dateTime.withTimeAtStartOfDay()

  def truncateToDay(dateMillis: Long): DateTime = truncateToDay(new DateTime(dateMillis))

  def numInclusiveDaysBetween(low: DateTime, high: DateTime, timeZone: DateTimeZone): Int = {
    val localLow = low.withZone(timeZone).toLocalDate()
    val localHigh = high.withZone(timeZone).toLocalDate()
    Days.daysBetween(localLow, localHigh).getDays + 1
  }

  def daysFrom(low: DateTime, high: DateTime, timeZone: DateTimeZone): List[DateTime] = {
    var dayMaker = low.withZone(timeZone).withTimeAtStartOfDay()
    val limit = high.withZone(timeZone).withMillisOfSecond(0).plusMillis(1)

    Iterator.continually {
      val d = dayMaker
      dayMaker = dayMaker.plusDays(1)
      d
    }.takeWhile(d => d.isBefore(limit)).toList
  }

  def daysFrom(lowInclusive: LocalDate, highInclusive: LocalDate): Seq[LocalDate] = {
    var dayMaker = lowInclusive
    val limit = highInclusive.plusDays(1)

    Iterator.continually {
      val d = dayMaker
      dayMaker = dayMaker.plusDays(1)
      d
    }.takeWhile(d => d.isBefore(limit)).toList
  }

  def daysFrom(yearMonth: YearMonth): Seq[LocalDate] = {
    val start = yearMonth.toLocalDate(1)
    daysFrom(start, start.dayOfMonth().withMaximumValue())
  }

  def startOfMonthsFrom(lowInclusive: LocalDate, highInclusive: LocalDate): List[LocalDate] = {
    var monthMaker = lowInclusive
    val limit = highInclusive.plusDays(1)

    Iterator.continually {
      val d = monthMaker
      monthMaker = monthMaker.plusMonths(1)
      d
    }.takeWhile(d => d.isBefore(limit)).toList
  }


  def hoursFrom(firstDayInclusive: LocalDate, lastDayInclusive: LocalDate): List[DateTime] = {
    var hourMaker = firstDayInclusive.toDateTimeAtStartOfDay(DateTimeZone.UTC)
    val limit = lastDayInclusive.plusDays(1).toDateTimeAtStartOfDay(DateTimeZone.UTC)

    Iterator.continually {
      val hour = hourMaker
      hourMaker = hourMaker.plusHours(1)
      hour
    }.takeWhile(_.isBefore(limit)).toList
  }

  def hoursFrom(firstHourInclusive: DateTime, lastHourInclusive: DateTime): Seq[DateTime] = {
    hoursFrom(firstHourInclusive, lastHourInclusive, firstHourInclusive.getZone)
  }
  def hoursFrom(firstHourInclusive: DateTime, lastHourInclusive: DateTime, timeZone: DateTimeZone): Seq[DateTime] = {
    var hourMaker = firstHourInclusive.withZone(timeZone)
    val limit = lastHourInclusive.withZone(timeZone).withMillisOfSecond(0).plusMillis(1)

    Iterator.continually {
      val hour = hourMaker
      hourMaker = hourMaker.plusHours(1)
      hour
    }.takeWhile(_.isBefore(limit)).toList
  }

  def utcDaysFrom(low: DateTime, high: DateTime): Seq[DateTime] = {
    daysFrom(low, high, DateTimeZone.UTC)
  }

  def estDaysFrom(low: DateTime, high: DateTime): Seq[DateTime] = {
    daysFrom(low, high, TimeZones.EST_ZONE)
  }

  def firstDayOfMonth(month: YearMonth, timeZone: DateTimeZone): DateTime = {
    new DateTime(timeZone)
      .withYear(month.getYear)
      .withMonthOfYear(month.getMonthOfYear)
      .withDayOfMonth(1)
      .withTimeAtStartOfDay()
  }

  def firstDayOfNextMonth(month: YearMonth, timeZone: DateTimeZone): DateTime = {
    firstDayOfMonth(month, timeZone).plusMonths(1).withTimeAtStartOfDay()
  }

  def startOfNextDay(time: DateTime, timeZone: DateTimeZone): DateTime = {
    time.withZone(timeZone).plusDays(1).withTimeAtStartOfDay()
  }

  def yearMonthOf(d: DateTime): YearMonth = new YearMonth(d.getYear, d.getMonthOfYear)

  def localDateOf(d: DateTime): LocalDate = new LocalDate(d.getYear, d.getMonthOfYear, d.getDayOfMonth)

  def fiveSecondFloorString(time:DateTime): String = {
    fiveSecondFloor(time).toString("yyyy-MM-dd/HH/mm:ss")
  }

  def fiveSecondFloor(time: DateTime): DateTime = {
    val fiveSecondStart: Int = time.getSecondOfMinute / 5 * 5
    time.withSecondOfMinute(fiveSecondStart).withMillisOfSecond(0)
  }

  def fiveMinuteCeil(time: DateTime): DateTime = {
    val startOfHour = DateTimeUtil.truncateToHour(time)
    val millisecondsElapsedInHour = time.getMillis - startOfHour.getMillis
    val partialMinutes = millisecondsElapsedInHour / 1.minute.toMillis.toDouble
    val fiveMinuteBlock: Int = Math.ceil(partialMinutes / 5d).toInt * 5
    startOfHour.plusMinutes(fiveMinuteBlock)
  }

  def hoursFor(days:Seq[LocalDate]): Seq[DateTime] = {
    DateTimeUtil.hoursFrom(DateTimeUtil.min(days:_*), DateTimeUtil.max(days:_*))
  }

  def startOfClientDayInUTC(browserRepresentation: String, clientTimeZone: DateTimeZone): DateTime = {
    DateTimeFormats.CalendarDateFormatter.parseDateAsDateTime(browserRepresentation, clientTimeZone).withTimeAtStartOfDay().withZone(DateTimeZone.UTC)
  }

  def endOfClientDayInUTC(browserRepresentation: String, clientTimeZone: DateTimeZone): DateTime = {
    DateTimeFormats.CalendarDateFormatter.parseDateAsDateTime(
      browserRepresentation,
      clientTimeZone
    ).plusDays(1).withTimeAtStartOfDay().minusMinutes(1).withZone(DateTimeZone.UTC)
  }

  def endOfClientDayInUTC(millis: Long, clientTimeZone: DateTimeZone): DateTime = {
    new DateTime(millis, clientTimeZone).plusDays(1).withTimeAtStartOfDay().minusMinutes(1).withZone(DateTimeZone.UTC)
  }

  def isBetween(startDate: DateTime, endDate: DateTime, time: DateTime): Boolean = {
    new Interval(startDate, endDate).contains(time)
  }

  def secondsBetween(start:Long, end:Long): Long = {
    (end-start)/1000
  }

  def isInPastInclusive(date: DateTime): Boolean = {
    val now = new DateTime()
    date.isBefore(now) || date == now
  }

  def toDateTimeAtEndOfDay(day: LocalDate) = {
    day.toDateTime(new LocalTime(23, 59, 59, 999))
  }
}

