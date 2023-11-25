package org.spacemc.spaceutils.utils

import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
object AsyncUtil {
    fun runAsync(plugin: JavaPlugin, fn: () -> Unit) {
        object : BukkitRunnable() {
            override fun run() {
                fn()
            }
        }.runTaskAsynchronously(plugin)
    }

    fun runAsyncLater(plugin: JavaPlugin, ticks: Long, fn: () -> Unit) {
        object : BukkitRunnable() {
            override fun run() {
                fn()
            }
        }.runTaskLaterAsynchronously(plugin, ticks)
    }

    fun runSync(plugin: JavaPlugin, fn: () -> Unit) {
        object : BukkitRunnable() {
            override fun run() {
                fn()
            }
        }.runTask(plugin)
    }

    fun runSyncLater(plugin: JavaPlugin, ticks: Long, fn: () -> Unit) {
        object : BukkitRunnable() {
            override fun run() {
                fn()
            }
        }.runTaskLater(plugin, ticks)
    }
}