package org.spacemc.utils.gui

import org.bukkit.Material

abstract class PaginatedGUIColored : PaginatedGUI() {
    override val forwardItemMaterial: Material = Material.GREEN_STAINED_GLASS_PANE
    override val backItemMaterial: Material = Material.RED_STAINED_GLASS_PANE
    override val closeItemMaterial: Material = Material.BLUE_STAINED_GLASS_PANE
}