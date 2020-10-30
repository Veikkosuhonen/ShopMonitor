package com.github.veikkosuhonen.shopmonitor

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

class Installer(val installNames: HashMap<UUID, String>,
                val monitors: HashMap<UUID, MutableList<Monitor>>,
                val monitorLocations: HashMap<Location, Monitor>,
) {

    fun install(location: Location, id: UUID) {
        if (!installNames[id].isNullOrEmpty()) {
            val player = Bukkit.getPlayer(id) ?: return
            if (monitorLocations.containsKey(location)) {
                Messager.sendMessage(player, "§cCannot install: there already is a monitor here!§r")
                return
            }
            val monitor = Monitor(location, installNames[id]!!, id)
            Messager.sendMessage(player, "§3Installed §6§l" + monitor.name)
            installNames[id] = ""
            monitors[id]?.let {
                it.add(monitor)
                monitorLocations[location] = monitor
                return
            }
            monitors[id] = mutableListOf(monitor)
            monitorLocations[location] = monitor
        }
    }
}