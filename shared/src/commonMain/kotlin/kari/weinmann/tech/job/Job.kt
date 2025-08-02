package kari.weinmann.tech.job

import kari.weinmann.tech.timetracker.TimetrackerInterface
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.Period
import java.time.temporal.TemporalAdjusters
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


class Job(val timeTracker: TimetrackerInterface, val weeklyHours: Int) {
    @OptIn(ExperimentalTime::class)
    suspend fun getDeltaFrom(from: LocalDate): Int{
        val today = Clock.System.now().epochSeconds/86400
        val start = from.toEpochDays()
        val days = today-start
        val requiredHours = days*weeklyHours/7
        val spent = timeTracker.getSpentTime(from).spent/60
        return requiredHours.toInt() - spent
    }
    @OptIn(ExperimentalTime::class)
    suspend fun getWeekSaldo():Int{
        val today = LocalDate.fromEpochDays(Clock.System.now().epochSeconds/86400)
        val lastMonday = today.dayOfWeek
        return 0
    }
}