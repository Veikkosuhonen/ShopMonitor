package main.kotlin.veikmaster.shopmonitor

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.util.*
import kotlin.collections.HashSet

class OPCommand(val trueSight: HashSet<UUID>,): CommandExecutor {

    override fun onCommand(cs: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        val player = Bukkit.getPlayer(cs.name) ?: return false
        if (args.isEmpty()) return false

        when (args[0]) {
            "truesight" -> {
                if (!trueSight.remove(player.uniqueId)) {
                    Messager.sendMessage(player, "§3§oTrue sight enabled")
                    trueSight.add(player.uniqueId)
                } else {
                    Messager.sendMessage(player, "§3§oTrue sight disabled")
                }
                return true
            }
        }
        return false
    }
}