package org.mwatt.base.util.time

import org.joda.time.DateTimeZone

//noinspection ScalaWeakerAccess
object TimeZones {
  val PST = "America/Los_Angeles"
  val MST = "America/Denver"
  val CST = "America/Chicago"
  val EST = "America/New_York"

  val UTC = "UTC"

  val EST_ZONE: DateTimeZone = DateTimeZone.forID(EST)
  val CST_ZONE: DateTimeZone = DateTimeZone.forID(CST)
  val MST_ZONE: DateTimeZone = DateTimeZone.forID(MST)
  val PST_ZONE: DateTimeZone = DateTimeZone.forID(PST)
  val UTC_ZONE: DateTimeZone = DateTimeZone.forID(UTC)

  val CONTIGUOUS_USA_ZONES: Set[DateTimeZone] = Set(PST_ZONE, MST_ZONE, CST_ZONE, EST_ZONE)

  val LEGAL_TIME_ZONE_IDS: Seq[String] = Seq(
    "US/Hawaii",
    "US/Alaska",
    "US/Pacific", PST,
    "US/Mountain", MST,
    "US/Arizona",
    "US/Central", CST,
    "US/Eastern", EST,
    "UTC"
  )

}
