package com.github.veikkosuhonen.shopmonitor.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class OPCommandTabCompleter: TabCompleter {
    override fun onTabComplete(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): MutableList<String> {
        return mutableListOf("truesight")
    }
}