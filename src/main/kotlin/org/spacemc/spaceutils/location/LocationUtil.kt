package org.spacemc.spaceutils.location

import org.bukkit.Location
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

@Suppress("unused")
fun Location.circle2D(radius: Double, precision: Int = 16): List<Location> {
    val prec = precision.coerceAtLeast(4)

    val positions = mutableListOf<Location>()

    for (angle in 0..360 step prec) {
        val x = radius * sin(angle.toDouble())
        val z = radius * cos(angle.toDouble())
        val loc = this.clone()
        loc.x += x
        loc.z += z
        positions.add(loc)
    }

    return positions
}

@Suppress("unused")
fun Location.interpolate2D(other: Location, precision: Double = 1.0): List<Location> {
    val positions = mutableListOf<Location>()

    val start = this.clone()
    start.y += 1.5
    val dir = other.toVector().subtract(this.toVector()).normalize()
    dir.multiply(precision)

    val numberAfterDot = precision.toString().split(".")[1]

    val numbersAfterDot = numberAfterDot.length

    val powerOfTen = 10.0.pow(numbersAfterDot.toDouble())

    var multiplyValue = 0.0
    multiplyValue = if (precision < 1.0) {
        powerOfTen / ("0.$numberAfterDot".toDouble() * powerOfTen)
    } else {
        1 / (precision.toInt() + 1).toDouble()
    }

    if (multiplyValue == 0.0) {
        multiplyValue = 1.0
    }

    repeat(start.distance(other).toInt() * multiplyValue.toInt()) {
        start.add(dir)

        positions.add(start.clone())
    }
    return positions
}
