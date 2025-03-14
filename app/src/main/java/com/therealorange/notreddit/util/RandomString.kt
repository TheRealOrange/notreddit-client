package com.therealorange.notreddit.util

import java.security.SecureRandom
import java.util.*

class RandomString(length: Int, random: Random?, symbols: String) {
    fun nextString(): String {
        for (idx in buf.indices) buf[idx] = symbols[random.nextInt(symbols.size)]
        return String(buf)
    }

    private val random: Random
    private val symbols: CharArray
    private val buf: CharArray
    @JvmOverloads
    constructor(length: Int = 21, random: Random? = SecureRandom()) : this(
        length,
        random,
        alphanum
    ) {
    }

    companion object {
        const val upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lower = upper.toLowerCase(Locale.ROOT)
        const val digits = "0123456789"
        val alphanum =
            upper + lower + digits
    }

    init {
        require(length >= 1)
        require(symbols.length >= 2)
        this.random = Objects.requireNonNull(random)!!
        this.symbols = symbols.toCharArray()
        buf = CharArray(length)
    }
}