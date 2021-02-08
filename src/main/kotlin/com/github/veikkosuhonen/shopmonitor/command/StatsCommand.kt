package com.github.veikkosuhonen.shopmonitor.command

import com.github.veikkosuhonen.shopmonitor.stats.Statistics
import com.github.veikkosuhonen.shopmonitor.ui.Messager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StatsCommand(val statistics: Statistics): CommandExecutor {

    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        val player = Bukkit.getPlayer(sender.name) ?: return false
        if (args.isEmpty()) {
            Messager.sendGeneralStats(player, statistics.getMonitorCount(), statistics.getUserCount(), statistics.getAvgMonitorsPerUser())
            return true
        } else if (args.isNotEmpty()) {
            try {
                val material = Material.valueOf(args.joinToString("_").toUpperCase())
                Messager.sendMaterialStats(player, material, statistics.getVolumeOfMaterial(material));
                return true
            } catch (iae: IllegalArgumentException) {
                Messager.sendMessage(player, "§cError: that material does not exist§r")
                return true
            }
        }
        return false
    }
}