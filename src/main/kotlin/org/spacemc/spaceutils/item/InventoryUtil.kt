package org.spacemc.spaceutils.item

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

@Suppress("unused")
object InventoryUtil {
    fun removeItemsFromInv(player: Player, item: ItemStack, amount: Int): Boolean {
        val inv = player.inventory
        if (!inv.containsAtLeast(item, amount)) {
            return false
        }

        val itemForRemoval = ItemBuilder(item.clone())
            .amount(amount)
            .make()

        inv.removeItem(itemForRemoval)
        return true
    }

    fun addItemsToPlayerInventory(player: Player, vararg items: ItemStack?) {
        for (item in items) {
            addItemToPlayer(player, item)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun addItemToPlayer(player: Player, item: ItemStack?) {
        val inventory = player.inventory
        val firstEmpty = inventory.firstEmpty()
        if (firstEmpty == -1) {
            dropItemAtPlayer(player, item)
            return
        }
        inventory.addItem(item!!)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun dropItemAtPlayer(player: Player, item: ItemStack?) {
        val location = player.location.add(0.0, 1.0, 0.0)
        val world = location.world
        world.dropItemNaturally(location, item!!)
    }
}