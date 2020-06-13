package utils

import java.util.*
import kotlin.random.Random

object CustomCalendar {
    private val r = Random(42)
    fun getInstanceCalendar(): Date {
        return GregorianCalendar.getInstance().time
    }

    fun getSpecificCalendar(year: Int, month: Int, day: Int): Date {
        val c = GregorianCalendar.getInstance()
        c.set(year, month, day)
        return c.time
    }

    fun getRandomCalendar(): Date {
        val c = GregorianCalendar()
        val year = r.nextInt(1970, Calendar.YEAR + 1)
        c.set(Calendar.YEAR, year)
        val dayOfYear = r.nextInt(1, c.getActualMaximum(Calendar.DAY_OF_YEAR))
        c.set(Calendar.DAY_OF_YEAR, dayOfYear)
        return c.time

    }

}