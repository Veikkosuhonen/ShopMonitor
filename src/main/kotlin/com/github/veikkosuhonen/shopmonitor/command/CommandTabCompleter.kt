package com.github.veikkosuhonen.shopmonitor.command

import com.github.veikkosuhonen.shopmonitor.monitor.Monitor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.*
import kotlin.collections.HashMap

class CommandTabCompleter(val monitors: HashMap<UUID, MutableList<Monitor>>): TabCompleter {

    val SUBCOMMANDS = mutableListOf("view", "install", "rename", "delete", "clear")

    override fun onTabComplete(cs: CommandSender, p1: Command, p2: String, args: Array<out String>): MutableList<String> {
        val monitorsNames = monitors[Bukkit.getPlayer(cs.name)?.uniqueId]?.map {it.name} ?: mutableListOf<String>()
        return when (args.size) {
            0 -> SUBCOMMANDS
            1 -> SUBCOMMANDS.filter{ it.startsWith(args[0]) }.toMutableList()
            2 -> monitorsNames.filter{ it.startsWith(args[1])}.toMutableList()
            else -> mutableListOf("")
        }
    }
}