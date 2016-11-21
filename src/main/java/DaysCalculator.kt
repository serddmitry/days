import java.time.DayOfWeek
import java.time.LocalDate.now
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by Dmitry Serdiuk on 11-Oct-16.
 */


fun main(args: Array<String>) {
    val spaceLength = 12
    val langs = args.toMutableList()
    require(langs.size == 7) { "Expecting exactly 7 parameters (got $langs), for example: EN EN EN EN RU DE CH" }
    val langsSeq = (1..15).flatMap { Collections.shuffle(langs); langs }
    val langsIter = langsSeq.iterator()
    val monthFormat = DateTimeFormatter.ofPattern("MMMM")
    val weekDay = DateTimeFormatter.ofPattern("EEE")

    repeat(now().withDayOfMonth(1).dayOfWeek.value - 1) {langsIter.next()}
    (0..2).forEach {
        val firstDay = now().plusMonths(it.toLong()).withDayOfMonth(1)
        println(firstDay.format(monthFormat))
        DayOfWeek.values().forEach { print(weekDay.format(it).padStart(spaceLength, ' ')) }
        println("\n" + "-".repeat(84))

        repeat(firstDay.dayOfWeek.value - 1) {
            print(" ".repeat(spaceLength))
        }

        generateSequence(firstDay) { it.plusDays(1) }.takeWhile { it.month == firstDay.month }.forEach {
            print((it.dayOfMonth.toString() + ": " + langsIter.next()).padStart(spaceLength, ' '))
            if (it.dayOfWeek == DayOfWeek.SUNDAY) println()
        }
        println("\n\n")
    }

}