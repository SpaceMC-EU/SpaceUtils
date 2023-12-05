package org.spacemc.spaceutils.text

import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

@Suppress("unused")
object NumberFormatter {
    val decimalFormatter: DecimalFormat = DecimalFormat("#.###")

    fun prettyCount(number: Number): String? {
        val suffix = arrayOf(" ", "k", "mln", "bln", "T", "P", "E")
        val numValue = number.toLong()
        val value = floor(log10(numValue.toDouble())).toInt()
        val base = value / 3
        return if (value >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(numValue / 10.0.pow((base * 3).toDouble())) + suffix[base]
        } else {
            DecimalFormat("#,##0").format(numValue)
        }
    }

    fun roundToDecimalPlaces(number: Number, decimalPlaces: Int = 2): String {
        val inputValue = number.toDouble()
        return "%.${decimalPlaces}f".format(inputValue)
    }
}