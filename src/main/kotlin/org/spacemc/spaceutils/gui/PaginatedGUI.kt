package org.spacemc.spaceutils.gui

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane
import com.github.stefvanschie.inventoryframework.pane.StaticPane
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.spacemc.spaceutils.item.ItemBuilder

/**
 * It's basically a class that creates paginated gui
 */
@Suppress("unused")
abstract class PaginatedGUI {
    abstract val backPaneMsg: Component
    abstract val backPaneMsgLore: Component
    abstract val forwardPaneMsg: Component
    abstract val forwardPaneLore: Component
    abstract val closePaneMsg: Component
    abstract val closePaneLore: Component

    abstract val chestGUITitle: Component

    abstract val forwardItemMaterial: Material
    abstract val backItemMaterial: Material
    abstract val closeItemMaterial: Material

    @Suppress("MemberVisibilityCanBePrivate")
    lateinit var gui: ChestGui

    @Suppress("MemberVisibilityCanBePrivate")
    var paginatedPane: PaginatedPane = PaginatedPane(0, 0, 9, 4)


    /**
     * Initializes pane basic functions for example go back go forward or close GUI
     * Creates ChestGUI function use it before you do anything with gui
     * Doesn't touch anything else
     */
    fun initPane() {
        gui = ChestGui(6, ComponentHolder.of(chestGUITitle))
        val backItem =
            ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .name(backPaneMsg)
                .lore(backPaneMsgLore)
                .make()
        val forwardItem =
            ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                .name(forwardPaneMsg)
                .lore(forwardPaneLore)
                .make()

        val closeItem =
            ItemBuilder(Material.BLUE_STAINED_GLASS_PANE)
                .name(closePaneMsg)
                .lore(closePaneLore)
                .make()

        val forwardPane = StaticPane(6, 4, 3, 2)
        forwardPane.fillWith(forwardItem) {
            if(paginatedPane.pages-1 > paginatedPane.page) {
                paginatedPane.page++
                gui.update()
            }
        }

        val backPane = StaticPane(0, 4, 3, 2)
        backPane.fillWith(backItem) {
            if(paginatedPane.page > 0) {
                paginatedPane.page--
                gui.update()
            }
        }

        val closePane = StaticPane(3, 4, 3, 2)
        closePane.fillWith(closeItem) {
            it.whoClicked.closeInventory()
        }

        gui.addPane(backPane)
        gui.addPane(closePane)
        gui.addPane(forwardPane)
    }
}