package org.spacemc.utils.item

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object InventoryUtil {
    fun addItemsToPlayerInventory(player: Player, vararg items: ItemStack?) {
        for (item in items) {
            addItemToPlayer(player, item)
        }
    }

    fun addItemToPlayer(player: Player, item: ItemStack?) {
        val inventory = player.inventory
        val firstEmpty = inventory.firstEmpty()
        if (firstEmpty == -1) dropItemAtPlayer(player, item)
        inventory.addItem(item!!)
    }

    fun dropItemAtPlayer(player: Player, item: ItemStack?) {
        val location = player.location.add(0.0, 1.0, 0.0)
        val world = location.world
        world.dropItemNaturally(location, item!!)
    }
}