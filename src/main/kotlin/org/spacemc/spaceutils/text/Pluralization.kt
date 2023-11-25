package org.spacemc.spaceutils.text

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

@Suppress("unused")
enum class PluralizationLists(val list: List<String>) {
    // 1, 2, 5
    DENOMINATOR_SINGULAR(listOf("", "y", "ów")), // Mianowik. Co? punkt, punkty, punktów
    GENITIVE_SINGULAR(listOf("u", "ów", "ów")), // Dopełniacz. Czego? punktu, punktów, punktów
}


@Suppress("unused")
object PluralizationPlaceholder {
    fun pluralize(placeholder: String, number: Number): TagResolver.Single {
        return pluralize(placeholder, number, PluralizationLists.DENOMINATOR_SINGULAR.list)
    }

    fun pluralize(placeholder: String, number: Number, pluralizationList: PluralizationLists): TagResolver.Single {
        return pluralize(placeholder, number, pluralizationList.list)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun pluralize(placeholder: String, number: Number, list: List<String>): TagResolver.Single {
        return Placeholder.parsed(placeholder, list[getPluralForm(number).toInt()])
    }

    private fun getPluralForm(amount: Number): Number {
        val amountLong = amount.toLong()
        return when {
            amount == 1L -> 0 // 1 dom
            amountLong % 10 in 2..4 && (amountLong % 100 < 10 || amountLong % 100 >= 20) -> 1 // 2 domy
            else -> 2 // 5 domów
        }
    }
}