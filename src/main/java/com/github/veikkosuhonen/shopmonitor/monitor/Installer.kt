package com.github.veikkosuhonen.shopmonitor.monitor

import com.github.veikkosuhonen.shopmonitor.ui.Messager
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

    fun delete(monitor: Monitor?, player: Player) {
        if (monitors[monitor?.ownerId]?.remove(monitor) != true) {
            Messager.sendMessage(player, "§cCannot find monitor '${monitor?.name}'§r")
        } else {
            monitorLocations.remove(monitor?.location)
            Messager.sendMessage(player, "§6§l${monitor?.name}§3 deleted§r")
        }
    }

    fun setToInstall(id: UUID, name: String) {
        installNames[id] = name
    }
}