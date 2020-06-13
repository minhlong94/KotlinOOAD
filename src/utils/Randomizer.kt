package utils

import kotlin.random.Random

object Randomizer {
    private val normalChar = ArrayList<Char>()
    private val capitalChar = ArrayList<Char>()
    private val r = Random(42) // set seed for random.

    init {
        var charCounter = 'a'
        var capCharCounter = 'A'
        for (x in 0 until 26) {
            normalChar.add(charCounter)
            capitalChar.add(capCharCounter)
            charCounter++
            capCharCounter++
        }
    }

    // Randomize a string consists of 12 numbers, seperated by "-", for example: "1111-2222-3333"
    fun randomNumbersAsString(): String {
        val result = StringBuilder()
        for (x in 0 until 12) {
            result.append(r.nextInt(0, 9).toString())
            if ((x + 1) % 4 == 0 && x != 0 && x != 11) result.append('-')
        }
        return result.toString()
    }

    // Randomize a 16-length string consists of lowercase and uppercase Alphabet characters, for example: "bnqCmZInczAQziBE"
    fun randomString(): String {
        val result = StringBuilder()
        for (x in 0 until 16) {
            val i = r.nextBoolean()
            if (i) result.append(normalChar[r.nextInt(0, 26)])
            else result.append(capitalChar[r.nextInt(0, 26)])
        }
        return result.toString()
    }
}