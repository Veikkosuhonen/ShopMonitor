package com.github.veikkosuhonen.shopmonitor.command

import com.github.veikkosuhonen.shopmonitor.monitor.Installer
import com.github.veikkosuhonen.shopmonitor.ui.Messager
import com.github.veikkosuhonen.shopmonitor.monitor.Monitor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.util.*
import kotlin.collections.HashMap

class Command(
        val monitors: HashMap<UUID, MutableList<Monitor>>,
        val installer: Installer
): CommandExecutor {

    override fun onCommand(cs: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        val player = Bukkit.getPlayer(cs.name) ?: return false

        if (args.isEmpty()) {
            Messager.sendView(player, monitors[player.uniqueId])
            return true
        } //default to view

        when(args[0]) {

            "install" -> {
                if (args.size != 2) {
                    return false
                }
                Messager.sendMessage(player, "§3Open a container to install §6§l" + args[1])
                installer.setToInstall(player.uniqueId, args[1])
                return true
            }

            "view" -> {
                Messager.sendView(player, monitors[player.uniqueId])
                return true
            }

            "rename" -> {
                if (args.size != 3) {
                    return false
                }
                val monitors = monitors[player.uniqueId]
                if (!monitors.isNullOrEmpty()) {
                    monitors.find { it.name == args[2] }?.let {
                        Messager.sendMessage(player, "§cYou already have a monitor §6§l" + it.name + "§c, choose another name§r")
                        return true
                    }
                    monitors.find { it.name == args[1] }?.let {
                        it.name = args[2]
                        Messager.sendMessage(player, "§6§l${args[1]}§3 renamed to §6§l${args[2]}§r")
                        return true
                    }
                }
                Messager.sendMessage(player, "§cCannot find monitor '${args[1]}'§r")
            }

            "clear" -> {
                if (args.size != 2) {
                    return false
                }
                val monitor = monitors[player.uniqueId]?.find { it.name == args[1] }
                if (monitor != null) {
                    monitor.clear()
                } else {
                    Messager.sendMessage(player, "§cCannot find monitor '${args[1]}'§r")
                }
                return true
            }

            "delete" -> {
                if (args.size == 3 && args[2] == "confirm") {
                    val monitor = monitors[player.uniqueId]?.find { it.name == args[1] }
                    installer.delete(monitor, player)
                    return true
                } else if (args.size == 2) {
                    monitors[player.uniqueId]?.find { it.name == args[1] }?.let {
                        Messager.sendDeleteConfirmMessage(player, it.name)
                        return true
                    }
                    Messager.sendMessage(player, "§cCannot find monitor '${args[1]}'§r")
                    return true
                }
                return true
            }

        }
        return false
    }
}